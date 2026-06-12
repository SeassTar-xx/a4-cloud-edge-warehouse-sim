# A4 基于翼辉开放云边端协同系统的智能仓储仿真

本项目是中国软件杯 A4 题的独立工程初始化版本，主线是智能仓储仿真、多 AGV 调度、货架机器人上架、避碰路径规划、容量/占用/库存映射一致性。

`wms_extension/multimodal-management-system/` 来自现有 WMS 项目，只作为云侧 WMS、仓储业务扩展、库存映射展示和演示素材。它不是 A4 仿真核心，也不作为平台评测的必要依赖。核心算法必须能在平台内独立运行。

## 当前第一阶段目标

平台接入前的本地算法内核验证。当前阶段优先保证 `a4_core/` 可以在无第三方依赖的环境中完成编译、单元测试和 benchmark，为后续接入翼辉平台接口打稳定基础。

## 核心算法模块

- `a4_core/model.py`：货物、AGV、传送带、交接区、盘位、货架、机器人、仿真状态。
- `a4_core/grid.py`：网格地图、障碍物、邻接点、Manhattan 距离。
- `a4_core/reservation_table.py`：顶点预约、边预约、对向换边冲突检测。
- `a4_core/path_planner.py`：时间扩展 A*，支持 WAIT。
- `a4_core/dispatcher.py`：保守任务分配和同目标合并。
- `a4_core/shelf_robot.py`：货架机器人同体积批处理和上架。
- `a4_core/invariants.py`：碰撞、容量、批处理、库存映射约束检查。
- `a4_core/simulator.py`：本地固定场景仿真。

## 本地运行

```bash
python3 -m unittest discover -s tests
python3 -m a4_core.benchmark
```

也可以运行：

```bash
sh scripts/run_core_tests.sh
```

## 队友协作

- 算法和平台集成优先修改 `a4_core/`、`platform_modules/`、`docs/function_interface.md`。
- 视频和演示材料优先修改 `docs/video_script.md`，原始视频放在 `videos/raw/`，不要提交压缩包。
- 文档和测试优先维护 `docs/`、`tests/`、`data/sample_scenario.json` 和实验记录。

## 当前完成度

- 已完成标准库小型仿真内核。
- 已完成 unittest 基础覆盖。
- 已完成平台适配占位模块。
- 已复制 WMS 扩展资产并记录来源。

## 后续接入翼辉平台步骤

1. 在答疑群确认平台最终评测边界、第三方库限制、地图/状态/指令接口。
2. 将平台 DTO 映射到 `a4_core.model`。
3. 替换 `platform_modules/*/main.py` 中的 TODO 适配层。
4. 使用平台真实地图和任务流补充实验记录。
5. 每次修改后运行 `python3 -m unittest discover -s tests`。
