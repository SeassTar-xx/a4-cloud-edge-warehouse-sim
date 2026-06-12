# 平台模块说明

本目录用于把 `a4_core` 的标准库算法内核包装成翼辉开放云边端协同系统中的可迁移模块。当前阶段只提供适配边界，不假装知道平台未公开接口。

- `cloud_dispatcher`：云侧全局任务分配、策略参数下发。
- `edge_scheduler`：边侧实时路径规划、路权预约、局部重规划。
- `agv_controller`：端侧 AGV 状态封装和指令下发。
- `shelf_robot_controller`：货架机器人上架任务执行。
- `handoff_manager`：交接区容量、占用、释放。
- `slot_manager`：货架盘位和库存映射。

接入平台时，优先替换各模块 `Adapter` 的输入输出协议，保留 `a4_core` 内核约束检查逻辑。平台账号、令牌、接口地址不得写入仓库。

