import unittest

from a4_core.model import AGV, CapacityError, Cargo, Point, ValidationError


class ModelTest(unittest.TestCase):
    def test_cargo_volume_must_be_legal(self):
        Cargo(id="ok", volume=0.5, target_shelf_id="S1")
        with self.assertRaises(ValidationError):
            Cargo(id="bad", volume=0.75, target_shelf_id="S1")

    def test_agv_load_capacity(self):
        agv = AGV(id="A1", position=Point(0, 0))
        agv.load([Cargo(id="C1", volume=0.5, target_shelf_id="S1")])
        with self.assertRaises(CapacityError):
            agv.load([Cargo(id="C2", volume=1.0, target_shelf_id="S1")])


if __name__ == "__main__":
    unittest.main()

