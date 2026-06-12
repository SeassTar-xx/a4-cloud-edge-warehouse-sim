import unittest

from a4_core.grid import GridMap
from a4_core.model import Point
from a4_core.path_planner import PathPlanner
from a4_core.reservation_table import ReservationTable


class PathPlannerTest(unittest.TestCase):
    def test_astar_finds_path(self):
        planner = PathPlanner(GridMap(4, 4), ReservationTable(), horizon=20)
        result = planner.plan(Point(0, 0), Point(3, 0), 0, "A1")
        self.assertTrue(result.success, result.failure_reason)
        path = result.path
        self.assertIsNotNone(path)
        self.assertEqual(path[0], Point(0, 0))
        self.assertEqual(path[-1], Point(3, 0))

    def test_astar_waits_or_reroutes_for_reservation(self):
        table = ReservationTable()
        table.reserve_vertex(Point(1, 0), 1, "A2")
        planner = PathPlanner(GridMap(3, 2), table, horizon=20)
        result = planner.plan(Point(0, 0), Point(2, 0), 0, "A1")
        self.assertTrue(result.success, result.failure_reason)
        path = result.path
        self.assertIsNotNone(path)
        self.assertNotEqual(path[1], Point(1, 0))

    def test_failure_has_reason(self):
        grid = GridMap(3, 1, obstacles={Point(1, 0)})
        planner = PathPlanner(grid, ReservationTable(), horizon=3)
        result = planner.plan(Point(0, 0), Point(2, 0), 0, "A1")
        self.assertFalse(result.success)
        self.assertIn("no safe path", result.failure_reason)

    def test_two_agvs_avoid_head_on_conflict_in_narrow_area(self):
        table = ReservationTable()
        grid = GridMap(3, 2)
        planner = PathPlanner(grid, table, horizon=20)
        first = planner.plan(Point(0, 0), Point(2, 0), 0, "A1")
        self.assertTrue(first.success, first.failure_reason)
        table.reserve_path(first.path, 0, "A1")

        second = planner.plan(Point(2, 0), Point(0, 0), 0, "A2")
        self.assertTrue(second.success, second.failure_reason)
        self.assertEqual(planner.validate_path(second.path, 0, "A2"), [])


if __name__ == "__main__":
    unittest.main()
