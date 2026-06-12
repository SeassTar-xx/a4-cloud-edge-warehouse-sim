"""Small deterministic simulation used by tests and benchmark."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List

from .dispatcher import ConservativeDispatcher
from .grid import GridMap
from .invariants import check_state
from .model import AGV, AGVStatus, Cargo, Conveyor, HandoffZone, Point, Shelf, ShelfRobot, SimulationState, Slot
from .path_planner import PathPlanner
from .reservation_table import ReservationTable
from .shelf_robot import ShelfRobotPlanner


def load_scenario(path: str | Path) -> Dict[str, Any]:
    with open(path, "r", encoding="utf-8") as f:
        return json.load(f)


def build_state(scenario: Dict[str, Any]) -> tuple[SimulationState, GridMap]:
    grid = GridMap.from_obstacles(scenario["grid"]["width"], scenario["grid"]["height"], scenario["grid"].get("obstacles", []))
    conveyors = {}
    for item in scenario["conveyors"]:
        conveyors[item["id"]] = Conveyor(
            id=item["id"],
            position=Point(*item["position"]),
            queue=[Cargo(**cargo) for cargo in item.get("queue", [])],
        )
    shelves = {}
    for shelf in scenario["shelves"]:
        shelves[shelf["id"]] = Shelf(
            id=shelf["id"],
            slots=[Slot(id=slot["id"], shelf_id=shelf["id"], max_volume=slot["max_volume"]) for slot in shelf["slots"]],
        )
    state = SimulationState(
        agvs={item["id"]: AGV(id=item["id"], position=Point(*item["position"])) for item in scenario["agvs"]},
        conveyors=conveyors,
        handoff_zones={
            item["id"]: HandoffZone(id=item["id"], shelf_id=item["shelf_id"], position=Point(*item["position"]))
            for item in scenario["handoff_zones"]
        },
        shelves=shelves,
        shelf_robots={
            item["id"]: ShelfRobot(id=item["id"], shelf_ids=list(item["shelf_ids"]), busy_until=item.get("busy_until", 0))
            for item in scenario["shelf_robots"]
        },
    )
    state.record_positions()
    return state, grid


def default_scenario() -> Dict[str, Any]:
    cargos = [
        {"id": "C1", "volume": 1.0, "target_shelf_id": "S1", "created_at": 0},
        {"id": "C2", "volume": 0.5, "target_shelf_id": "S2", "created_at": 1},
        {"id": "C3", "volume": 0.5, "target_shelf_id": "S2", "created_at": 2},
        {"id": "C4", "volume": 0.25, "target_shelf_id": "S3", "created_at": 3},
        {"id": "C5", "volume": 0.25, "target_shelf_id": "S3", "created_at": 4},
        {"id": "C6", "volume": 0.25, "target_shelf_id": "S3", "created_at": 5},
        {"id": "C7", "volume": 0.25, "target_shelf_id": "S3", "created_at": 6},
        {"id": "C8", "volume": 1.0, "target_shelf_id": "S4", "created_at": 7},
    ]
    return {
        "grid": {"width": 10, "height": 8, "obstacles": [[4, 2], [4, 3], [4, 4]]},
        "conveyors": [
            {"id": "CV1", "position": [0, 1], "queue": cargos[:4]},
            {"id": "CV2", "position": [0, 6], "queue": cargos[4:]},
        ],
        "agvs": [
            {"id": "A1", "position": [1, 0]},
            {"id": "A2", "position": [1, 2]},
            {"id": "A3", "position": [1, 5]},
            {"id": "A4", "position": [1, 7]},
        ],
        "handoff_zones": [
            {"id": "HZ1", "shelf_id": "S1", "position": [8, 1]},
            {"id": "HZ2", "shelf_id": "S2", "position": [8, 3]},
            {"id": "HZ3", "shelf_id": "S3", "position": [8, 5]},
            {"id": "HZ4", "shelf_id": "S4", "position": [8, 7]},
        ],
        "shelves": [
            {"id": sid, "slots": [{"id": f"{sid}-P{i}", "max_volume": 1.0} for i in range(1, 7)]}
            for sid in ("S1", "S2", "S3", "S4")
        ],
        "shelf_robots": [
            {"id": "R1", "shelf_ids": ["S1", "S2"]},
            {"id": "R2", "shelf_ids": ["S3", "S4"]},
        ],
    }


def run_simulation(scenario: Dict[str, Any] | None = None) -> Dict[str, Any]:
    scenario = scenario or default_scenario()
    state, grid = build_state(scenario)
    reservations = ReservationTable()
    planner = PathPlanner(grid, reservations, horizon=80)
    dispatcher = ConservativeDispatcher(grid)
    robot_planner = ShelfRobotPlanner()
    total_cargo = sum(len(c.queue) for c in state.conveyors.values())
    home_positions = {agv.id: agv.position for agv in state.agvs.values()}
    assigned_cargo: Dict[str, List[Cargo]] = {}
    time = 0

    for agv in state.agvs.values():
        agv.home_position = home_positions[agv.id]

    while len(state.inventory) < total_cargo and time < 500:
        state.time = time
        _dispatch_one_safe_task(state, grid, reservations, planner, dispatcher, assigned_cargo, time)
        _advance_agvs_one_tick(state, reservations, planner, assigned_cargo, time)
        _run_shelf_robots(state, robot_planner, time)
        errors = check_state(state)
        if errors:
            state.invariant_errors.extend(f"t={time}: {error}" for error in errors)
        state.record_positions()
        state.event_log.append(f"t={time}: inventory={len(state.inventory)}/{total_cargo}")
        time += 1

    state.time = time
    errors = check_state(state)
    invariant_errors = list(dict.fromkeys(state.invariant_errors + errors))
    total_wait = sum(agv.wait_ticks for agv in state.agvs.values())
    average_wait_time = total_wait / max(1, len(state.agvs))
    return {
        "completed": len(state.inventory) == total_cargo,
        "total_time": state.time,
        "collision": any("collision" in e or "edge swap" in e for e in invariant_errors),
        "capacity_violation": any("capacity" in e or "exceeds" in e for e in invariant_errors),
        "inventory_violation": any("inventory" in e for e in invariant_errors),
        "errors": invariant_errors,
        "invariant_errors": invariant_errors,
        "agv_distance": {agv.id: agv.distance_travelled for agv in state.agvs.values()},
        "average_wait_time": average_wait_time,
        "handoff_wait_count": {zone.id: zone.wait_count for zone in state.handoff_zones.values()},
        "inventory_count": len(state.inventory),
    }


def _dispatch_one_safe_task(
    state: SimulationState,
    grid: GridMap,
    reservations: ReservationTable,
    planner: PathPlanner,
    dispatcher: ConservativeDispatcher,
    assigned_cargo: Dict[str, List[Cargo]],
    time: int,
) -> None:
    forbidden = {agv.position for agv in state.agvs.values() if agv.status is AGVStatus.IDLE}
    idle_agvs = [agv for agv in state.agvs.values() if agv.status is AGVStatus.IDLE]
    if not idle_agvs:
        return
    decision = dispatcher.select_task(
        idle_agvs,
        state.conveyors.values(),
        state.handoff_zones.values(),
        state.shelf_robots.values(),
        time,
        planner=None,
    )
    if decision is None:
        return

    agv = state.agvs[decision.agv_id]
    conveyor = state.conveyors[decision.conveyor_id]
    zone = next(z for z in state.handoff_zones.values() if z.shelf_id == decision.target_shelf_id)
    batch = [c for c in conveyor.queue if c.id in set(decision.cargo_ids)]
    if not batch or not agv.can_load(batch) or not zone.can_accept(batch):
        return

    forbidden_without_self = {point for point in forbidden if point != agv.position}
    pickup_result = planner.plan(agv.position, conveyor.position, time, agv.id, forbidden_points=forbidden_without_self)
    if not pickup_result.success or pickup_result.path is None:
        agv.wait_ticks += 1
        return
    pickup_time = time + len(pickup_result.path) - 1
    buffer_lock = f"buffer_lock:{conveyor.id}"
    if not reservations.is_resource_window_free(buffer_lock, pickup_time, pickup_time, agv.id):
        agv.wait_ticks += 1
        return

    dropoff_result = planner.plan(
        conveyor.position,
        zone.position,
        pickup_time,
        agv.id,
        forbidden_points=forbidden_without_self,
    )
    if not dropoff_result.success or dropoff_result.path is None:
        agv.wait_ticks += 1
        return
    dropoff_time = pickup_time + len(dropoff_result.path) - 1
    handoff_lock = f"handoff_zone_lock:{zone.id}"
    if not reservations.is_resource_window_free(handoff_lock, dropoff_time, dropoff_time, agv.id):
        agv.wait_ticks += 1
        return

    full_path = pickup_result.path + dropoff_result.path[1:]
    try:
        reservations.reserve_path(full_path, time, agv.id)
        reservations.reserve_resource(buffer_lock, pickup_time, pickup_time, agv.id)
        reservations.reserve_resource(handoff_lock, dropoff_time, dropoff_time, agv.id)
    except ValueError:
        agv.wait_ticks += 1
        return

    assigned_cargo[agv.id] = ConservativeDispatcher.remove_batch(conveyor.queue, decision.cargo_ids)
    agv.target_conveyor_id = conveyor.id
    agv.target_handoff_id = zone.id
    agv.target_shelf_id = zone.shelf_id
    agv.assigned_cargo_ids = list(decision.cargo_ids)
    agv.assign_path(full_path)
    agv.transition(AGVStatus.TO_PICKUP, time, f"assigned {decision.cargo_ids} from {conveyor.id} to {zone.id}")
    state.event_log.append(f"t={time}: dispatch {agv.id} -> {decision.cargo_ids}")


def _advance_agvs_one_tick(
    state: SimulationState,
    reservations: ReservationTable,
    planner: PathPlanner,
    assigned_cargo: Dict[str, List[Cargo]],
    time: int,
) -> None:
    for agv in state.agvs.values():
        if agv.status is AGVStatus.IDLE:
            continue
        if agv.status is AGVStatus.WAIT_HANDOFF:
            _try_drop_or_wait(state, reservations, planner, agv, time)
            continue
        if agv.status is AGVStatus.BLOCKED:
            agv.wait_ticks += 1
            continue

        moved = agv.step()
        if moved:
            state.event_log.append(f"t={time}: {agv.id} moved to {agv.position}")

        if agv.status is AGVStatus.TO_PICKUP and agv.target_conveyor_id is not None:
            conveyor = state.conveyors[agv.target_conveyor_id]
            if agv.position == conveyor.position:
                agv.transition(AGVStatus.PICKING, time, f"arrived at {conveyor.id}")
                batch = assigned_cargo.pop(agv.id, [])
                try:
                    agv.load(batch)
                except Exception as exc:
                    agv.transition(AGVStatus.ERROR, time, str(exc))
                    continue
                agv.transition(AGVStatus.TO_HANDOFF, time, f"loaded {[c.id for c in batch]}")

        if agv.status is AGVStatus.TO_HANDOFF and agv.target_handoff_id is not None:
            zone = state.handoff_zones[agv.target_handoff_id]
            if agv.position == zone.position:
                _try_drop_or_wait(state, reservations, planner, agv, time)

        if agv.status is AGVStatus.RETURNING and agv.home_position is not None and agv.position == agv.home_position:
            agv.transition(AGVStatus.IDLE, time, "returned home")
            agv.clear_task()


def _try_drop_or_wait(
    state: SimulationState,
    reservations: ReservationTable,
    planner: PathPlanner,
    agv: AGV,
    time: int,
) -> None:
    if agv.target_handoff_id is None:
        agv.transition(AGVStatus.ERROR, time, "missing handoff target")
        return
    zone = state.handoff_zones[agv.target_handoff_id]
    if not zone.can_accept(agv.loaded):
        zone.wait_count += 1
        agv.wait_ticks += 1
        agv.transition(AGVStatus.WAIT_HANDOFF, time, f"{zone.id} has no capacity")
        return
    handoff_lock = f"handoff_zone_lock:{zone.id}"
    if not reservations.is_resource_window_free(handoff_lock, time, time, agv.id):
        zone.wait_count += 1
        agv.wait_ticks += 1
        agv.transition(AGVStatus.WAIT_HANDOFF, time, f"{zone.id} lock unavailable")
        return

    agv.transition(AGVStatus.DROPPING, time, f"dropping at {zone.id}")
    zone.accept(agv.unload_all())
    if agv.home_position is None:
        agv.transition(AGVStatus.IDLE, time, "no home position")
        agv.clear_task()
        return
    result = planner.plan(agv.position, agv.home_position, time, agv.id)
    if not result.success or result.path is None:
        agv.transition(AGVStatus.BLOCKED, time, result.failure_reason)
        return
    try:
        reservations.reserve_path(result.path, time, agv.id)
    except ValueError as exc:
        agv.transition(AGVStatus.BLOCKED, time, str(exc))
        return
    agv.assign_path(result.path)
    agv.transition(AGVStatus.RETURNING, time, "returning to home")


def _run_shelf_robots(state: SimulationState, robot_planner: ShelfRobotPlanner, time: int) -> None:
    for robot in state.shelf_robots.values():
        if robot.busy_until > time:
            continue
        zones = list(state.handoff_zones.values())
        choice = robot_planner.choose_batch(robot, zones, time)
        if choice is None:
            continue
        zone, batch = choice
        robot_planner.store_batch(robot, zone, batch, state.shelves, state.inventory)
        robot.busy_until = time + max(1, len(batch))
        state.event_log.append(f"t={time}: {robot.id} stored {[cargo.id for cargo in batch]} from {zone.id}")
