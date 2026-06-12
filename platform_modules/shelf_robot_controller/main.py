"""Shelf robot controller adapter placeholder."""

from a4_core.shelf_robot import ShelfRobotPlanner


class ShelfRobotControllerAdapter:
    """Adapter boundary for shelf robot storage tasks."""

    def __init__(self):
        self.planner = ShelfRobotPlanner()

    def choose_and_store(self, robot, zones, shelves, inventory, time):
        # TODO: Replace local objects with platform robot task DTOs.
        choice = self.planner.choose_batch(robot, zones, time)
        if choice is None:
            return None
        zone, batch = choice
        self.planner.store_batch(robot, zone, batch, shelves, inventory)
        return [cargo.id for cargo in batch]


if __name__ == "__main__":
    print("shelf_robot_controller adapter placeholder; no platform robot is contacted.")

