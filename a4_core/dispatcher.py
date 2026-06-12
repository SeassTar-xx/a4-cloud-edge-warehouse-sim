"""Conservative task dispatcher for AGV cargo pickup."""

from __future__ import annotations

from dataclasses import dataclass
from typing import Iterable, List, Optional

from .grid import GridMap
from .model import AGV, AGVStatus, Cargo, Conveyor, HandoffZone, Point, ShelfRobot
from .path_planner import PathPlanner


@dataclass
class DispatchDecision:
    agv_id: str
    conveyor_id: str
    cargo_ids: List[str]
    target_shelf_id: str
    score: float
    pickup_path: List[Point] | None = None
    dropoff_path: List[Point] | None = None


class ConservativeDispatcher:
    def __init__(self, grid: GridMap) -> None:
        self.grid = grid

    def select_task(
        self,
        agvs: Iterable[AGV],
        conveyors: Iterable[Conveyor],
        handoff_zones: Iterable[HandoffZone],
        shelf_robots: Iterable[ShelfRobot],
        time: int,
        planner: PathPlanner | None = None,
    ) -> Optional[DispatchDecision]:
        zone_by_shelf = {z.shelf_id: z for z in handoff_zones}
        robot_by_shelf = {sid: robot for robot in shelf_robots for sid in robot.shelf_ids}
        best: Optional[DispatchDecision] = None

        for conveyor in conveyors:
            if not conveyor.queue or not conveyor.is_available(time):
                continue
            candidates = self._candidate_batches(conveyor.queue)
            for batch in candidates:
                target = batch[0].target_shelf_id
                zone = zone_by_shelf.get(target)
                if zone is None or not zone.can_accept(batch):
                    continue
                for agv in agvs:
                    if agv.status is not AGVStatus.IDLE:
                        continue
                    if not agv.can_load(batch):
                        continue
                    pickup_path = None
                    dropoff_path = None
                    if planner is not None:
                        pickup_result = planner.plan(agv.position, conveyor.position, time, agv.id)
                        if not pickup_result.success or pickup_result.path is None:
                            continue
                        pickup_arrival = time + len(pickup_result.path) - 1
                        dropoff_result = planner.plan(conveyor.position, zone.position, pickup_arrival, agv.id)
                        if not dropoff_result.success or dropoff_result.path is None:
                            continue
                        pickup_path = pickup_result.path
                        dropoff_path = dropoff_result.path
                    distance = self.grid.manhattan(agv.position, conveyor.position)
                    congestion = zone.used_volume * 10.0
                    robot = robot_by_shelf.get(target)
                    robot_busy = 3.0 if robot and robot.busy_until > time else 0.0
                    merge_bonus = max(0, len(batch) - 1) * 4.0
                    score = distance + congestion + robot_busy - merge_bonus
                    decision = DispatchDecision(
                        agv.id,
                        conveyor.id,
                        [c.id for c in batch],
                        target,
                        score,
                        pickup_path=pickup_path,
                        dropoff_path=dropoff_path,
                    )
                    if best is None or decision.score < best.score:
                        best = decision
        return best

    @staticmethod
    def _candidate_batches(queue: List[Cargo]) -> List[List[Cargo]]:
        if not queue:
            return []
        first = queue[0]
        if first.volume == 1.0:
            return [[first]]
        max_count = 2 if first.volume == 0.5 else 4
        same_target = [c for c in queue if c.target_shelf_id == first.target_shelf_id and c.volume == first.volume]
        batch: List[Cargo] = []
        total = 0.0
        for cargo in same_target:
            if len(batch) >= max_count or total + cargo.volume > 1.0:
                break
            batch.append(cargo)
            total += cargo.volume
        return [batch]

    @staticmethod
    def remove_batch(queue: List[Cargo], cargo_ids: Iterable[str]) -> List[Cargo]:
        wanted = set(cargo_ids)
        picked = [c for c in queue if c.id in wanted]
        queue[:] = [c for c in queue if c.id not in wanted]
        return picked
