"""Time-space reservation table for AGV path planning."""

from __future__ import annotations

from dataclasses import dataclass, field
from typing import Dict, Tuple

from .model import Point


VertexKey = Tuple[Point, int]
EdgeKey = Tuple[Point, Point, int]


@dataclass
class ReservationTable:
    vertices: Dict[VertexKey, str] = field(default_factory=dict)
    edges: Dict[EdgeKey, str] = field(default_factory=dict)

    def reserve_vertex(self, point: Point, time: int, agv_id: str) -> None:
        owner = self.vertices.get((point, time))
        if owner is not None and owner != agv_id:
            raise ValueError(f"vertex {point} at t={time} reserved by {owner}")
        self.vertices[(point, time)] = agv_id

    def reserve_edge(self, from_point: Point, to_point: Point, time: int, agv_id: str) -> None:
        key = (from_point, to_point, time)
        reverse_key = (to_point, from_point, time)
        owner = self.edges.get(key)
        reverse_owner = self.edges.get(reverse_key)
        if owner is not None and owner != agv_id:
            raise ValueError(f"edge {from_point}->{to_point} at t={time} reserved by {owner}")
        if reverse_owner is not None and reverse_owner != agv_id:
            raise ValueError(f"reverse edge {to_point}->{from_point} at t={time} reserved by {reverse_owner}")
        self.edges[key] = agv_id

    def is_vertex_free(self, point: Point, time: int, agv_id: str) -> bool:
        owner = self.vertices.get((point, time))
        return owner is None or owner == agv_id

    def is_edge_free(self, from_point: Point, to_point: Point, time: int, agv_id: str) -> bool:
        owner = self.edges.get((from_point, to_point, time))
        reverse_owner = self.edges.get((to_point, from_point, time))
        return (owner is None or owner == agv_id) and (reverse_owner is None or reverse_owner == agv_id)

    def reserve_path(self, path: list[Point], start_time: int, agv_id: str) -> None:
        for offset, point in enumerate(path):
            self.reserve_vertex(point, start_time + offset, agv_id)
            if offset:
                self.reserve_edge(path[offset - 1], point, start_time + offset - 1, agv_id)

