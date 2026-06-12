import unittest

from a4_core.simulator import default_scenario, run_simulation


class SimulatorTest(unittest.TestCase):
    def test_sample_scenario_completes(self):
        summary = run_simulation(default_scenario())
        self.assertTrue(summary["completed"], summary)
        self.assertFalse(summary["collision"], summary)
        self.assertFalse(summary["capacity_violation"], summary)
        self.assertFalse(summary["inventory_violation"], summary)


if __name__ == "__main__":
    unittest.main()

