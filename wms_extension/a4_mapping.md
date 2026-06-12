# WMS 与 A4 映射

| WMS 概念 | A4 核心概念 | 说明 |
| --- | --- | --- |
| 商品/库存 | Cargo / inventory | WMS 可展示库存结果，真实一致性由 `a4_core` 检查 |
| 仓库库位 | Shelf / Slot | A4 需要盘位占用与库存映射一致 |
| 出入库任务 | Conveyor / DispatchDecision | A4 更关注平台内任务调度和 AGV 执行 |
| 后台页面 | 云侧展示 | 不作为平台评分主链路 |

