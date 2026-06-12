"""Cloud dispatcher adapter placeholder."""

from a4_core.dispatcher import ConservativeDispatcher


class CloudDispatcherAdapter:
    """Adapter boundary for platform task dispatch."""

    def __init__(self, grid):
        self.dispatcher = ConservativeDispatcher(grid)

    def select_task(self, agvs, conveyors, handoff_zones, shelf_robots, time):
        # TODO: Replace plain objects with real Yihui platform task/state DTOs.
        return self.dispatcher.select_task(agvs, conveyors, handoff_zones, shelf_robots, time)


if __name__ == "__main__":
    print("cloud_dispatcher adapter placeholder; connect platform DTOs before deployment.")

