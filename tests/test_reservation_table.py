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


if __name__ == "__main__":
    unittest.main()

