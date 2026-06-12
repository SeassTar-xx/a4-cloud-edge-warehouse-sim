"""Time-expanded A* path planner."""

from __future__ import annotations

import heapq
from typing import Dict, List, Optional, Tuple

from .grid import GridMap
from .model import Point
from .reservation_table import ReservationTable


State = Tuple[Point, int]


class PathPlanner:
    def __init__(self, grid: GridMap, reservation_table: ReservationTable, horizon: int = 100) -> None:
        self.grid = grid
        self.reservations = reservation_table
        self.horizon = horizon

    def plan(self, start: Point, goal: Point, start_time: int, agv_id: str) -> Optional[List[Point]]:
        if not self.grid.is_valid(start) or not self.grid.is_valid(goal):
            raise ValueError("start and goal must be valid grid points")

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
                return self._reconstruct(came_from, current_state)
            if current_time - start_time >= self.horizon:
                continue

            for nxt in self.grid.neighbors(current, include_wait=True):
                next_time = current_time + 1
                if not self.reservations.is_vertex_free(nxt, next_time, agv_id):
                    continue
                if not self.reservations.is_edge_free(current, nxt, current_time, agv_id):
                    continue
                next_state = (nxt, next_time)
                new_cost = cost + 1
                if next_state not in cost_so_far or new_cost < cost_so_far[next_state]:
                    cost_so_far[next_state] = new_cost
                    counter += 1
                    priority = new_cost + self.grid.manhattan(nxt, goal)
                    heapq.heappush(frontier, (priority, new_cost, counter, nxt))
                    came_from[next_state] = current_state
        return None

    @staticmethod
    def _reconstruct(came_from: Dict[State, Optional[State]], state: State) -> List[Point]:
        path: List[Point] = []
        while state is not None:
            point, _ = state
            path.append(point)
            state = came_from[state]
        path.reverse()
        return path

