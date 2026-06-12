"""Shelf robot storage policy."""

from __future__ import annotations

from typing import Dict, List, Optional

from .model import Cargo, HandoffZone, Shelf, ShelfRobot


class ShelfRobotPlanner:
    def choose_batch(self, robot: ShelfRobot, zones: List[HandoffZone], time: int) -> Optional[tuple[HandoffZone, List[Cargo]]]:
        eligible = [z for z in zones if z.shelf_id in robot.shelf_ids and z.waiting]
        eligible.sort(key=lambda z: (min(c.created_at for c in z.waiting), -z.used_volume))
        for zone in eligible:
            for volume in (1.0, 0.5, 0.25):
                same = [c for c in zone.waiting if c.volume == volume]
                if not same:
                    continue
                same.sort(key=lambda c: c.created_at)
                batch: List[Cargo] = []
                total = 0.0
                for cargo in same:
                    if total + cargo.volume <= 1.0:
                        batch.append(cargo)
                        total += cargo.volume
                if batch and robot.can_process(batch):
                    return zone, batch
        return None

    def store_batch(
        self,
        robot: ShelfRobot,
        zone: HandoffZone,
        batch: List[Cargo],
        shelves: Dict[str, Shelf],
        inventory: Dict[str, tuple[str, str]],
    ) -> None:
        if not robot.can_process(batch):
            raise ValueError("shelf robot can only process same-volume cargos with total volume <= 1.0")
        shelf = shelves[zone.shelf_id]
        for cargo in batch:
            slot = shelf.first_free_slot(cargo)
            if slot is None:
                raise ValueError(f"no free slot for cargo {cargo.id} on shelf {shelf.id}")
            slot.store(cargo)
            inventory[cargo.id] = (shelf.id, slot.id)
        zone.pop_batch(batch)
        robot.last_batch = list(batch)
