import unittest

from a4_core.simulator import default_scenario, run_simulation


class SimulatorTest(unittest.TestCase):
    def test_sample_scenario_completes(self):
        summary = run_simulation(default_scenario())
        self.assertTrue(summary["completed"], summary)
        self.assertFalse(summary["collision"], summary)
        self.assertFalse(summary["capacity_violation"], summary)
        self.assertFalse(summary["inventory_violation"], summary)

    def test_handoff_occupied_makes_later_agv_wait(self):
        scenario = default_scenario()
        scenario["conveyors"] = [
            {
                "id": "CV1",
                "position": [0, 1],
                "queue": [
                    {"id": "C1", "volume": 1.0, "target_shelf_id": "S1", "created_at": 0},
                ],
            },
            {
                "id": "CV2",
                "position": [0, 6],
                "queue": [
                    {"id": "C2", "volume": 1.0, "target_shelf_id": "S1", "created_at": 1},
                ],
            },
        ]
        scenario["shelf_robots"] = [{"id": "R1", "shelf_ids": ["S1"], "busy_until": 50}]
        summary = run_simulation(scenario)
        self.assertTrue(summary["completed"], summary)
        self.assertGreaterEqual(summary["handoff_wait_count"]["HZ1"], 1)

    def test_three_runs_are_deterministic(self):
        summaries = [run_simulation(default_scenario()) for _ in range(3)]
        comparable = [
            (
                summary["completed"],
                summary["total_time"],
                summary["collision"],
                summary["capacity_violation"],
                summary["inventory_violation"],
                summary["agv_distance"],
                summary["handoff_wait_count"],
            )
            for summary in summaries
        ]
        self.assertEqual(comparable[0], comparable[1])
        self.assertEqual(comparable[1], comparable[2])


if __name__ == "__main__":
    unittest.main()
