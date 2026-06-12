"""Small deterministic simulation used by tests and benchmark."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List

from .dispatcher import ConservativeDispatcher
from .grid import GridMap
from .invariants import check_state
from .model import AGV, Cargo, Conveyor, HandoffZone, Point, Shelf, ShelfRobot, SimulationState, Slot
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
            item["id"]: ShelfRobot(id=item["id"], shelf_ids=list(item["shelf_ids"]))
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
    time = 0

    while len(state.inventory) < total_cargo and time < 500:
        state.time = time
        decision = dispatcher.select_task(
            state.agvs.values(),
            state.conveyors.values(),
            state.handoff_zones.values(),
            state.shelf_robots.values(),
            time,
        )
        if decision is not None:
            agv = state.agvs[decision.agv_id]
            conveyor = state.conveyors[decision.conveyor_id]
            batch = ConservativeDispatcher.remove_batch(conveyor.queue, decision.cargo_ids)
            agv.load(batch)
            zone = next(z for z in state.handoff_zones.values() if z.shelf_id == decision.target_shelf_id)
            to_pick = planner.plan(agv.position, conveyor.position, time, agv.id)
            if to_pick is None:
                raise RuntimeError(f"no path for {agv.id} to conveyor")
            reservations.reserve_path(to_pick, time, agv.id)
            pickup_time = time + len(to_pick) - 1
            to_drop = planner.plan(conveyor.position, zone.position, pickup_time, agv.id)
            if to_drop is None:
                raise RuntimeError(f"no path for {agv.id} to handoff")
            reservations.reserve_path(to_drop, pickup_time, agv.id)
            drop_time = pickup_time + len(to_drop) - 1
            to_home = planner.plan(zone.position, home_positions[agv.id], drop_time, agv.id)
            if to_home is None:
                raise RuntimeError(f"no path for {agv.id} to parking point")
            reservations.reserve_path(to_home, drop_time, agv.id)
            path = to_pick + to_drop[1:] + to_home[1:]
            agv.distance_travelled += max(0, len(path) - 1)
            agv.position = home_positions[agv.id]
            state.agv_position_history.setdefault(agv.id, []).extend(path[1:])
            zone.accept(agv.unload_all())
            time += max(1, len(path) - 1)
            state.time = time

        for robot in state.shelf_robots.values():
            zones = list(state.handoff_zones.values())
            choice = robot_planner.choose_batch(robot, zones, time)
            if choice is None:
                continue
            zone, batch = choice
            robot_planner.store_batch(robot, zone, batch, state.shelves, state.inventory)
            robot.busy_until = time + len(batch)

        if decision is None:
            time += 1

    state.time = time
    errors = check_state(state)
    return {
        "completed": len(state.inventory) == total_cargo,
        "total_time": state.time,
        "collision": any("collision" in e or "edge swap" in e for e in errors),
        "capacity_violation": any("capacity" in e or "exceeds" in e for e in errors),
        "inventory_violation": any("inventory" in e for e in errors),
        "errors": errors,
        "agv_distance": {agv.id: agv.distance_travelled for agv in state.agvs.values()},
        "handoff_wait_count": {zone.id: zone.wait_count for zone in state.handoff_zones.values()},
        "inventory_count": len(state.inventory),
    }
