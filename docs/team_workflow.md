# 团队协作流程

## 角色

- 我：算法、Codex 协作、平台集成。我的分支主要用于算法内核和翼辉平台接入。
- 队友 A：视频、PPT、截图、演示脚本，不上传视频原片。
- 队友 B：文档、测试、答疑群记录、实验记录。

## 平台账号排班

如果平台账号只能一人使用，按固定时间段排班：算法调试优先白天长时间段，视频录制使用稳定版本，文档同学避免在算法联调时改平台配置。

## Git 分支策略

- `main` 只放稳定版本，必须能通过 compileall、unittest 和 benchmark。
- `dev/algorithm-core` 用于算法优化和仿真内核调整。
- `dev/platform-integration` 用于翼辉平台接入。
- `docs/video-materials` 用于文档、PPT 和视频素材说明，不上传视频原片。
- 合并前必须运行 unittest，并记录 benchmark 摘要。
