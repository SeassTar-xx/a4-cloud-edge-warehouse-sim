"""Domain model for the A4 warehouse simulation core."""

from __future__ import annotations

from dataclasses import dataclass, field
from enum import Enum
from typing import Dict, Iterable, List, Optional, Tuple


VALID_VOLUMES = {1.0, 0.5, 0.25}
EPSILON = 1e-9


class CapacityError(ValueError):
    """Raised when a capacity constraint would be violated."""


class ValidationError(ValueError):
    """Raised when an object violates a domain constraint."""


class AGVStatus(str, Enum):
    IDLE = "IDLE"
    TO_PICKUP = "TO_PICKUP"
    PICKING = "PICKING"
    TO_HANDOFF = "TO_HANDOFF"
    WAIT_HANDOFF = "WAIT_HANDOFF"
    DROPPING = "DROPPING"
    RETURNING = "RETURNING"
    BLOCKED = "BLOCKED"
    ERROR = "ERROR"


@dataclass(frozen=True, order=True)
class Point:
    x: int
    y: int


@dataclass
class Cargo:
    id: str
    volume: float
    target_shelf_id: str
    created_at: int = 0

    def __post_init__(self) -> None:
        if self.volume not in VALID_VOLUMES:
            raise ValidationError(f"invalid cargo volume {self.volume}; expected one of {sorted(VALID_VOLUMES)}")


@dataclass
class AGV:
    id: str
    position: Point
    capacity: float = 1.0
    loaded: List[Cargo] = field(default_factory=list)
    distance_travelled: int = 0
    status: AGVStatus = AGVStatus.IDLE
    path: List[Point] = field(default_factory=list)
    path_index: int = 0
    target_conveyor_id: Optional[str] = None
    target_handoff_id: Optional[str] = None
    target_shelf_id: Optional[str] = None
    assigned_cargo_ids: List[str] = field(default_factory=list)
    home_position: Optional[Point] = None
    wait_ticks: int = 0
    logs: List[str] = field(default_factory=list)

    @property
    def load_volume(self) -> float:
        return sum(c.volume for c in self.loaded)

    def can_load(self, cargos: Iterable[Cargo]) -> bool:
        return self.load_volume + sum(c.volume for c in cargos) <= self.capacity + EPSILON

    def load(self, cargos: Iterable[Cargo]) -> None:
        cargos = list(cargos)
        if not self.can_load(cargos):
            raise CapacityError(f"AGV {self.id} capacity exceeded")
        self.loaded.extend(cargos)

    def unload_all(self) -> List[Cargo]:
        cargos = list(self.loaded)
        self.loaded.clear()
        return cargos

    def transition(self, new_status: AGVStatus, time: int, reason: str) -> None:
        if self.status != new_status:
            self.logs.append(f"t={time} {self.id}: {self.status.value} -> {new_status.value}: {reason}")
        self.status = new_status

    def assign_path(self, path: List[Point]) -> None:
        self.path = list(path)
        self.path_index = 0

    def has_next_step(self) -> bool:
        return self.path_index + 1 < len(self.path)

    def step(self) -> bool:
        if not self.has_next_step():
            return False
        next_point = self.path[self.path_index + 1]
        if next_point != self.position:
            self.distance_travelled += 1
        self.position = next_point
        self.path_index += 1
        return True

    def clear_task(self) -> None:
        self.path = []
        self.path_index = 0
        self.target_conveyor_id = None
        self.target_handoff_id = None
        self.target_shelf_id = None
        self.assigned_cargo_ids = []
        self.wait_ticks = 0


@dataclass
class Conveyor:
    id: str
    position: Point
    queue: List[Cargo] = field(default_factory=list)
    occupied_until: int = 0

    def is_available(self, time: int) -> bool:
        return time >= self.occupied_until


@dataclass
class HandoffZone:
    id: str
    shelf_id: str
    position: Point
    capacity: float = 1.0
    waiting: List[Cargo] = field(default_factory=list)
    wait_count: int = 0

    @property
    def used_volume(self) -> float:
        return sum(c.volume for c in self.waiting)

    def can_accept(self, cargos: Iterable[Cargo]) -> bool:
        return self.used_volume + sum(c.volume for c in cargos) <= self.capacity + EPSILON

    def accept(self, cargos: Iterable[Cargo]) -> None:
        cargos = list(cargos)
        if not self.can_accept(cargos):
            self.wait_count += 1
            raise CapacityError(f"handoff zone {self.id} capacity exceeded")
        self.waiting.extend(cargos)

    def pop_batch(self, cargos: Iterable[Cargo]) -> List[Cargo]:
        selected = list(cargos)
        selected_ids = {c.id for c in selected}
        self.waiting = [c for c in self.waiting if c.id not in selected_ids]
        return selected


@dataclass
class Slot:
    id: str
    shelf_id: str
    max_volume: float
    cargo_id: Optional[str] = None
    cargo_volume: Optional[float] = None

    @property
    def is_free(self) -> bool:
        return self.cargo_id is None

    def can_store(self, cargo: Cargo) -> bool:
        return self.is_free and cargo.volume <= self.max_volume + EPSILON

    def store(self, cargo: Cargo) -> None:
        if not self.can_store(cargo):
            raise CapacityError(f"slot {self.id} cannot store cargo {cargo.id}")
        self.cargo_id = cargo.id
        self.cargo_volume = cargo.volume


@dataclass
class Shelf:
    id: str
    slots: List[Slot]

    def first_free_slot(self, cargo: Cargo) -> Optional[Slot]:
        for slot in self.slots:
            if slot.can_store(cargo):
                return slot
        return None


@dataclass
class ShelfRobot:
    id: str
    shelf_ids: List[str]
    busy_until: int = 0
    last_batch: List[Cargo] = field(default_factory=list)

    def can_process(self, cargos: Iterable[Cargo]) -> bool:
        cargos = list(cargos)
        if not cargos:
            return False
        volumes = {c.volume for c in cargos}
        return len(volumes) == 1 and sum(c.volume for c in cargos) <= 1.0 + EPSILON


@dataclass
class SimulationState:
    agvs: Dict[str, AGV]
    conveyors: Dict[str, Conveyor]
    handoff_zones: Dict[str, HandoffZone]
    shelves: Dict[str, Shelf]
    shelf_robots: Dict[str, ShelfRobot]
    inventory: Dict[str, Tuple[str, str]] = field(default_factory=dict)
    time: int = 0
    agv_position_history: Dict[str, List[Point]] = field(default_factory=dict)
    event_log: List[str] = field(default_factory=list)
    invariant_errors: List[str] = field(default_factory=list)

    def record_positions(self) -> None:
        for agv_id, agv in self.agvs.items():
            self.agv_position_history.setdefault(agv_id, []).append(agv.position)
