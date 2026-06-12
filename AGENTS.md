# Codex 工作约束

- 禁止直接安装依赖，不能运行 `brew install`、`curl` 安装脚本、`npm install`、`pip install`、`mvn package`、`docker pull`、`docker run`，除非用户明确确认。
- 禁止破坏原始 `multimodal-management-system` 仓库；本项目只使用复制后的 `wms_extension/`。
- 禁止提交平台账号、密码、token、私钥、`.env`。
- 平台核心算法优先使用 Python 标准库，避免评测环境依赖风险。
- 每次改代码必须跑 `python3 -m unittest discover -s tests`。
- 修改平台模块必须同步更新 `docs/function_interface.md`。
- 修改算法必须更新 `docs/experiment_log_template.csv` 或补充实验记录说明。
- 稳定性优先于速度；AGV 碰撞、容量违规、库存映射错误都可能导致得分归零。

