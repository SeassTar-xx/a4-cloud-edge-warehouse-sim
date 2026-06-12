import unittest

from a4_core.model import Point
from a4_core.reservation_table import ReservationTable


class ReservationTableTest(unittest.TestCase):
    def test_vertex_conflict(self):
        table = ReservationTable()
        p = Point(1, 1)
        table.reserve_vertex(p, 3, "A1")
        self.assertFalse(table.is_vertex_free(p, 3, "A2"))
        with self.assertRaises(ValueError):
            table.reserve_vertex(p, 3, "A2")

    def test_reverse_edge_conflict(self):
        table = ReservationTable()
        a = Point(0, 0)
        b = Point(1, 0)
        table.reserve_edge(a, b, 2, "A1")
        self.assertFalse(table.is_edge_free(b, a, 2, "A2"))
        with self.assertRaises(ValueError):
            table.reserve_edge(b, a, 2, "A2")

    def test_resource_lock_window(self):
        table = ReservationTable()
        table.reserve_resource("handoff_zone_lock:HZ1", 3, 5, "A1")
        self.assertFalse(table.is_resource_window_free("handoff_zone_lock:HZ1", 4, 6, "A2"))
        self.assertTrue(table.is_resource_window_free("handoff_zone_lock:HZ1", 6, 7, "A2"))

    def test_reserve_path_rolls_back_on_conflict(self):
        table = ReservationTable()
        table.reserve_vertex(Point(1, 0), 1, "A2")
        with self.assertRaises(ValueError):
            table.reserve_path([Point(0, 0), Point(1, 0), Point(2, 0)], 0, "A1")
        self.assertTrue(table.is_vertex_free(Point(0, 0), 0, "A3"))


if __name__ == "__main__":
    unittest.main()
