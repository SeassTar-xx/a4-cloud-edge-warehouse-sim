"""Time-space reservation table for AGV path planning."""

from __future__ import annotations

from dataclasses import dataclass, field
from typing import Dict, Iterable, List, Tuple

from .model import Point


VertexKey = Tuple[Point, int]
EdgeKey = Tuple[Point, Point, int]
ResourceKey = Tuple[str, int]


@dataclass
class ReservationTable:
    vertices: Dict[VertexKey, str] = field(default_factory=dict)
    edges: Dict[EdgeKey, str] = field(default_factory=dict)
    resource_locks: Dict[ResourceKey, str] = field(default_factory=dict)

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

    def reserve_resource(self, resource_id: str, start_time: int, end_time: int, owner_id: str) -> None:
        if end_time < start_time:
            raise ValueError("resource lock end_time must be >= start_time")
        written: List[ResourceKey] = []
        try:
            for time in range(start_time, end_time + 1):
                key = (resource_id, time)
                current_owner = self.resource_locks.get(key)
                if current_owner is not None and current_owner != owner_id:
                    raise ValueError(f"resource {resource_id} at t={time} locked by {current_owner}")
                already_owned = current_owner == owner_id
                self.resource_locks[key] = owner_id
                if not already_owned:
                    written.append(key)
        except Exception:
            for key in written:
                if self.resource_locks.get(key) == owner_id:
                    del self.resource_locks[key]
            raise

    def is_vertex_free(self, point: Point, time: int, agv_id: str) -> bool:
        owner = self.vertices.get((point, time))
        return owner is None or owner == agv_id

    def is_edge_free(self, from_point: Point, to_point: Point, time: int, agv_id: str) -> bool:
        owner = self.edges.get((from_point, to_point, time))
        reverse_owner = self.edges.get((to_point, from_point, time))
        return (owner is None or owner == agv_id) and (reverse_owner is None or reverse_owner == agv_id)

    def is_resource_free(self, resource_id: str, time: int, owner_id: str) -> bool:
        owner = self.resource_locks.get((resource_id, time))
        return owner is None or owner == owner_id

    def is_resource_window_free(self, resource_id: str, start_time: int, end_time: int, owner_id: str) -> bool:
        if end_time < start_time:
            return False
        return all(self.is_resource_free(resource_id, time, owner_id) for time in range(start_time, end_time + 1))

    def are_resources_window_free(
        self,
        resource_ids: Iterable[str],
        start_time: int,
        end_time: int,
        owner_id: str,
    ) -> bool:
        return all(self.is_resource_window_free(resource_id, start_time, end_time, owner_id) for resource_id in resource_ids)

    def reserve_path(
        self,
        path: list[Point],
        start_time: int,
        agv_id: str,
        locked_resources: Iterable[str] | None = None,
    ) -> None:
        written_vertices: List[VertexKey] = []
        written_edges: List[EdgeKey] = []
        written_resources: List[ResourceKey] = []
        locked_resources = list(locked_resources or [])
        try:
            for offset, point in enumerate(path):
                time = start_time + offset
                self.reserve_vertex(point, time, agv_id)
                written_vertices.append((point, time))
                if offset:
                    from_point = path[offset - 1]
                    edge_time = time - 1
                    self.reserve_edge(from_point, point, edge_time, agv_id)
                    written_edges.append((from_point, point, edge_time))
                for resource_id in locked_resources:
                    key = (resource_id, time)
                    already_owned = self.resource_locks.get(key) == agv_id
                    self.reserve_resource(resource_id, time, time, agv_id)
                    if not already_owned:
                        written_resources.append(key)
        except Exception:
            for key in written_vertices:
                if self.vertices.get(key) == agv_id:
                    del self.vertices[key]
            for key in written_edges:
                if self.edges.get(key) == agv_id:
                    del self.edges[key]
            for key in written_resources:
                if self.resource_locks.get(key) == agv_id:
                    del self.resource_locks[key]
            raise
