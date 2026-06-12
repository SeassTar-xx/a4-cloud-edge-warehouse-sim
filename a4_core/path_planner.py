"""Time-expanded A* path planner."""

from __future__ import annotations

import heapq
from dataclasses import dataclass
from typing import Dict, Iterable, List, Optional, Set, Tuple

from .grid import GridMap
from .model import Point
from .reservation_table import ReservationTable


State = Tuple[Point, int]


@dataclass
class PlanResult:
    path: Optional[List[Point]]
    success: bool
    failure_reason: str = ""


class PathPlanner:
    def __init__(self, grid: GridMap, reservation_table: ReservationTable, horizon: int = 100) -> None:
        self.grid = grid
        self.reservations = reservation_table
        self.horizon = horizon

    def plan(
        self,
        start: Point,
        goal: Point,
        start_time: int,
        agv_id: str,
        forbidden_points: Iterable[Point] | None = None,
        locked_resources: Iterable[str] | None = None,
    ) -> PlanResult:
        if not self.grid.is_valid(start) or not self.grid.is_valid(goal):
            raise ValueError("start and goal must be valid grid points")
        forbidden: Set[Point] = set(forbidden_points or [])
        resources = list(locked_resources or [])
        if start in forbidden:
            return PlanResult(None, False, f"start point {start} is forbidden")
        if goal in forbidden:
            return PlanResult(None, False, f"goal point {goal} is forbidden")

        frontier: list[tuple[int, int, int, Point]] = []
        counter = 0
        heapq.heappush(frontier, (self.grid.manhattan(start, goal), 0, counter, start))
        came_from: Dict[State, Optional[State]] = {(start, start_time): None}
        cost_so_far: Dict[State, int] = {(start, start_time): 0}

        while frontier:
            _, cost, _, current = heapq.heappop(frontier)
            current_time = start_time + cost
            current_state = (current, current_time)
            if current == goal:
                path = self._reconstruct(came_from, current_state)
                errors = self.validate_path(path, start_time, agv_id, forbidden, resources)
                if errors:
                    return PlanResult(None, False, "; ".join(errors))
                return PlanResult(path, True)
            if current_time - start_time >= self.horizon:
                continue

            for nxt in self.grid.neighbors(current, include_wait=True):
                if nxt in forbidden:
                    continue
                next_time = current_time + 1
                if not self.reservations.is_vertex_free(nxt, next_time, agv_id):
                    continue
                if not self.reservations.is_edge_free(current, nxt, current_time, agv_id):
                    continue
                if resources and not self.reservations.are_resources_window_free(resources, next_time, next_time, agv_id):
                    continue
                next_state = (nxt, next_time)
                new_cost = cost + 1
                if next_state not in cost_so_far or new_cost < cost_so_far[next_state]:
                    cost_so_far[next_state] = new_cost
                    counter += 1
                    priority = new_cost + self.grid.manhattan(nxt, goal)
                    heapq.heappush(frontier, (priority, new_cost, counter, nxt))
                    came_from[next_state] = current_state
        return PlanResult(None, False, f"no safe path from {start} to {goal} within horizon {self.horizon}")

    def validate_path(
        self,
        path: List[Point],
        start_time: int,
        agv_id: str,
        forbidden_points: Iterable[Point] | None = None,
        locked_resources: Iterable[str] | None = None,
    ) -> List[str]:
        errors: List[str] = []
        forbidden = set(forbidden_points or [])
        resources = list(locked_resources or [])
        if not path:
            return ["path is empty"]
        for offset, point in enumerate(path):
            time = start_time + offset
            if not self.grid.is_valid(point):
                errors.append(f"invalid point {point} at t={time}")
            if point in forbidden:
                errors.append(f"forbidden point {point} at t={time}")
            if not self.reservations.is_vertex_free(point, time, agv_id):
                errors.append(f"vertex {point} is not free at t={time}")
            if resources and not self.reservations.are_resources_window_free(resources, time, time, agv_id):
                errors.append(f"locked resource unavailable at t={time}: {resources}")
            if offset:
                prev = path[offset - 1]
                if point not in self.grid.neighbors(prev, include_wait=True):
                    errors.append(f"non-adjacent move {prev}->{point} at t={time - 1}")
                if not self.reservations.is_edge_free(prev, point, time - 1, agv_id):
                    errors.append(f"edge {prev}->{point} is not free at t={time - 1}")
        return errors

    @staticmethod
    def _reconstruct(came_from: Dict[State, Optional[State]], state: State) -> List[Point]:
        path: List[Point] = []
        while state is not None:
            point, _ = state
            path.append(point)
            state = came_from[state]
        path.reverse()
        return path
