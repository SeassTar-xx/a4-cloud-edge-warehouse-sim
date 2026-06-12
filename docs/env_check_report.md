# 环境检查报告

检查时间：2026-06-13  
执行原则：只读检查，不安装依赖，不启动服务。

## 路径与源仓库

- 当前路径 `pwd`：`/Users/xuxin/SCU/软件大三/中软杯`
- 源仓库根目录：`/Users/xuxin/SCU/软件大三/中软杯/multimodal-management-system`
- 源仓库 remote：
  - `origin git@github.com:SeassTar-xx/multimodal-management-system.git (fetch)`
  - `origin git@github.com:SeassTar-xx/multimodal-management-system.git (push)`
- 源仓库 `git status --short`：干净，无输出
- 源仓库 commit：`c8d3b4d8678f605a739c0c810792978a74a94af2`

## 系统

- `uname -a`：`Darwin xuxindeMacBook-Pro.local 25.5.0 Darwin Kernel Version 25.5.0: Mon Apr 27 20:41:12 PDT 2026; root:xnu-12377.121.6~2/RELEASE_ARM64_T6050 arm64`
- `sw_vers`：
  - `ProductName: macOS`
  - `ProductVersion: 26.5.1`
  - `BuildVersion: 25F80`

## 工具检查

| 工具 | 路径 | 版本/结果 |
| --- | --- | --- |
| git | `/usr/bin/git` | `git version 2.50.1 (Apple Git-155)` |
| gh | `/opt/homebrew/bin/gh` | `gh version 2.92.0 (2026-04-28)` |
| python3 | `/opt/homebrew/bin/python3` | `Python 3.14.5` |
| node | `/opt/homebrew/bin/node` | `v26.0.0` |
| npm | `/opt/homebrew/bin/npm` | `11.12.1` |
| java | `/usr/bin/java` | 系统 shim 报告未定位 Java Runtime；Maven 使用 Homebrew OpenJDK 25.0.2 |
| mvn | `/opt/homebrew/bin/mvn` | `Apache Maven 3.9.15` |
| docker | 缺失 | `command -v docker` 无输出 |
| mysql | `/opt/homebrew/bin/mysql` | 已存在 |
| redis-server | `/opt/homebrew/bin/redis-server` | 已存在 |

## 缺失工具

- `docker`

