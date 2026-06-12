"""Readable invariant checks for simulation state."""

from __future__ import annotations

from typing import List

from .model import EPSILON, SimulationState


def check_state(state: SimulationState) -> List[str]:
    errors: List[str] = []
    _check_current_agv_positions(state, errors)
    _check_agv_edge_swaps(state, errors)
    _check_agv_capacity(state, errors)
    _check_handoff_capacity(state, errors)
    _check_shelf_robot_batches(state, errors)
    _check_inventory_mapping(state, errors)
    return errors


def _check_current_agv_positions(state: SimulationState, errors: List[str]) -> None:
    seen: dict[object, str] = {}
    for agv in state.agvs.values():
        owner = seen.get(agv.position)
        if owner is not None:
            errors.append(f"AGV vertex collision: {owner} and {agv.id} at {agv.position}")
        seen[agv.position] = agv.id


def _check_agv_edge_swaps(state: SimulationState, errors: List[str]) -> None:
    histories = state.agv_position_history
    if not histories:
        return
    max_len = max(len(h) for h in histories.values())
    for t in range(max_len - 1):
        ids = list(histories)
        for i, aid in enumerate(ids):
            if t + 1 >= len(histories[aid]):
                continue
            for bid in ids[i + 1:]:
                if t + 1 >= len(histories[bid]):
                    continue
                if histories[aid][t] == histories[bid][t + 1] and histories[aid][t + 1] == histories[bid][t]:
                    errors.append(f"AGV edge swap conflict at t={t}: {aid} <-> {bid}")


def _check_agv_capacity(state: SimulationState, errors: List[str]) -> None:
    for agv in state.agvs.values():
        if agv.load_volume > agv.capacity + EPSILON:
            errors.append(f"AGV {agv.id} load volume {agv.load_volume} exceeds {agv.capacity}")


def _check_handoff_capacity(state: SimulationState, errors: List[str]) -> None:
    for zone in state.handoff_zones.values():
        if zone.used_volume > zone.capacity + EPSILON:
            errors.append(f"handoff zone {zone.id} used volume {zone.used_volume} exceeds {zone.capacity}")


def _check_shelf_robot_batches(state: SimulationState, errors: List[str]) -> None:
    for robot in state.shelf_robots.values():
        if not robot.last_batch:
            continue
        volumes = {cargo.volume for cargo in robot.last_batch}
        total = sum(cargo.volume for cargo in robot.last_batch)
        if len(volumes) > 1:
            errors.append(f"shelf robot {robot.id} mixed cargo volumes in one batch")
        if total > 1.0 + EPSILON:
            errors.append(f"shelf robot {robot.id} batch volume {total} exceeds 1.0")


def _check_inventory_mapping(state: SimulationState, errors: List[str]) -> None:
    slot_by_cargo: dict[str, tuple[str, str]] = {}
    for shelf in state.shelves.values():
        for slot in shelf.slots:
            if slot.cargo_id is not None:
                slot_by_cargo[slot.cargo_id] = (shelf.id, slot.id)
    if state.inventory != slot_by_cargo:
        errors.append(f"inventory mapping mismatch: inventory={state.inventory}, slots={slot_by_cargo}")

