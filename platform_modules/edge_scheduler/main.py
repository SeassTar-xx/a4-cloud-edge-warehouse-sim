"""Edge scheduler adapter placeholder."""

from a4_core.path_planner import PathPlanner


class EdgeSchedulerAdapter:
    """Adapter boundary for path planning and right-of-way reservation."""

    def __init__(self, grid, reservation_table, horizon=100):
        self.planner = PathPlanner(grid, reservation_table, horizon=horizon)

    def plan_path(self, start, goal, start_time, agv_id):
        # TODO: Map platform coordinates and time ticks to a4_core Point/time.
        # Returns PlanResult so platform callers can inspect failure_reason and choose WAIT/replan.
        return self.planner.plan(start, goal, start_time, agv_id)


if __name__ == "__main__":
    print("edge_scheduler adapter placeholder; platform map interface is pending.")
