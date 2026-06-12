import unittest

from a4_core.dispatcher import ConservativeDispatcher
from a4_core.model import Cargo


class DispatcherTest(unittest.TestCase):
    def test_medium_same_target_can_merge(self):
        queue = [
            Cargo(id="C1", volume=0.5, target_shelf_id="S1"),
            Cargo(id="C2", volume=0.5, target_shelf_id="S1"),
        ]
        batches = ConservativeDispatcher._candidate_batches(queue)
        self.assertEqual([[cargo.id for cargo in batch] for batch in batches], [["C1", "C2"]])

    def test_medium_different_target_cannot_merge(self):
        queue = [
            Cargo(id="C1", volume=0.5, target_shelf_id="S1"),
            Cargo(id="C2", volume=0.5, target_shelf_id="S2"),
        ]
        batches = ConservativeDispatcher._candidate_batches(queue)
        self.assertEqual([[cargo.id for cargo in batch] for batch in batches], [["C1"]])


if __name__ == "__main__":
    unittest.main()
