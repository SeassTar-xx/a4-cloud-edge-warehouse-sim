"""Benchmark entry point for the local A4 simulation core."""

from __future__ import annotations

from pathlib import Path
from pprint import pprint

from .simulator import load_scenario, run_simulation


def main() -> dict:
    root = Path(__file__).resolve().parents[1]
    scenario_path = root / "data" / "sample_scenario.json"
    scenario = load_scenario(scenario_path)
    summary = run_simulation(scenario)
    print("A4 智能仓储仿真 benchmark")
    print(f"完成: {summary['completed']}")
    print(f"完成时间: {summary['total_time']}")
    print(f"碰撞: {summary['collision']}")
    print(f"容量违规: {summary['capacity_violation']}")
    print(f"库存映射错误: {summary['inventory_violation']}")
    print("AGV 行驶步数:")
    pprint(summary["agv_distance"])
    print("交接区等待次数:")
    pprint(summary["handoff_wait_count"])
    print("summary dict:")
    pprint(summary)
    return summary


if __name__ == "__main__":
    main()

