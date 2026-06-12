import unittest

from a4_core.grid import GridMap
from a4_core.model import Point
from a4_core.path_planner import PathPlanner
from a4_core.reservation_table import ReservationTable


class PathPlannerTest(unittest.TestCase):
    def test_astar_finds_path(self):
        planner = PathPlanner(GridMap(4, 4), ReservationTable(), horizon=20)
        path = planner.plan(Point(0, 0), Point(3, 0), 0, "A1")
        self.assertIsNotNone(path)
        self.assertEqual(path[0], Point(0, 0))
        self.assertEqual(path[-1], Point(3, 0))

    def test_astar_waits_or_reroutes_for_reservation(self):
        table = ReservationTable()
        table.reserve_vertex(Point(1, 0), 1, "A2")
        planner = PathPlanner(GridMap(3, 2), table, horizon=20)
        path = planner.plan(Point(0, 0), Point(2, 0), 0, "A1")
        self.assertIsNotNone(path)
        self.assertNotEqual(path[1], Point(1, 0))


if __name__ == "__main__":
    unittest.main()

