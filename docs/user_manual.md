# 用户手册

## 本地测试

在项目根目录运行：

```bash
python3 -m unittest discover -s tests
```

或：

```bash
sh scripts/run_core_tests.sh
```

## Benchmark

```bash
python3 -m a4_core.benchmark
```

benchmark 会读取 `data/sample_scenario.json`，输出完成时间、碰撞、容量违规、库存映射错误、AGV 行驶步数和交接区等待次数。

## WMS 扩展

WMS 扩展位于 `wms_extension/multimodal-management-system/`。它只作为云侧业务管理和演示扩展，不是平台核心算法。

## 平台运行注意事项

- 先确认平台接口，再替换 `platform_modules/` 的 Adapter。
- 不要把账号密码、token、平台地址写入代码。
- 每次接入真实数据后都运行约束检查器。

