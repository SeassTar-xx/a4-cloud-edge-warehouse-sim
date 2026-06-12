"""Grid map utilities."""

from __future__ import annotations

from dataclasses import dataclass, field
from typing import Iterable, List, Set

from .model import Point


@dataclass
class GridMap:
    width: int
    height: int
    obstacles: Set[Point] = field(default_factory=set)

    @classmethod
    def from_obstacles(cls, width: int, height: int, obstacles: Iterable[Iterable[int]]) -> "GridMap":
        return cls(width=width, height=height, obstacles={Point(int(x), int(y)) for x, y in obstacles})

    def in_bounds(self, point: Point) -> bool:
        return 0 <= point.x < self.width and 0 <= point.y < self.height

    def passable(self, point: Point) -> bool:
        return point not in self.obstacles

    def is_valid(self, point: Point) -> bool:
        return self.in_bounds(point) and self.passable(point)

    def neighbors(self, point: Point, include_wait: bool = True) -> List[Point]:
        candidates = [
            Point(point.x + 1, point.y),
            Point(point.x - 1, point.y),
            Point(point.x, point.y + 1),
            Point(point.x, point.y - 1),
        ]
        if include_wait:
            candidates.append(point)
        return [p for p in candidates if self.is_valid(p)]

    @staticmethod
    def manhattan(a: Point, b: Point) -> int:
        return abs(a.x - b.x) + abs(a.y - b.y)

