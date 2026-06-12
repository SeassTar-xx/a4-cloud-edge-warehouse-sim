import unittest

from a4_core.invariants import check_state
from a4_core.model import AGV, Cargo, HandoffZone, Point, Shelf, ShelfRobot, SimulationState, Slot
from a4_core.shelf_robot import ShelfRobotPlanner


class InvariantsTest(unittest.TestCase):
    def test_handoff_capacity(self):
        zone = HandoffZone(id="HZ1", shelf_id="S1", position=Point(0, 0))
        zone.accept([Cargo(id="C1", volume=1.0, target_shelf_id="S1")])
        self.assertFalse(zone.can_accept([Cargo(id="C2", volume=0.25, target_shelf_id="S1")]))

    def test_shelf_robot_rejects_mixed_volume(self):
        robot = ShelfRobot(id="R1", shelf_ids=["S1"])
        batch = [
            Cargo(id="C1", volume=0.5, target_shelf_id="S1"),
            Cargo(id="C2", volume=0.25, target_shelf_id="S1"),
        ]
        self.assertFalse(robot.can_process(batch))
        with self.assertRaises(ValueError):
            ShelfRobotPlanner().store_batch(
                robot,
                HandoffZone(id="HZ1", shelf_id="S1", position=Point(0, 0), waiting=list(batch)),
                batch,
                {"S1": Shelf(id="S1", slots=[Slot(id="P1", shelf_id="S1", max_volume=1.0)])},
                {},
            )

    def test_inventory_mapping_matches_slots(self):
        cargo = Cargo(id="C1", volume=0.5, target_shelf_id="S1")
        slot = Slot(id="P1", shelf_id="S1", max_volume=1.0)
        slot.store(cargo)
        state = SimulationState(
            agvs={"A1": AGV(id="A1", position=Point(0, 0))},
            conveyors={},
            handoff_zones={},
            shelves={"S1": Shelf(id="S1", slots=[slot])},
            shelf_robots={},
            inventory={"C1": ("S1", "P1")},
        )
        self.assertEqual(check_state(state), [])


if __name__ == "__main__":
    unittest.main()

