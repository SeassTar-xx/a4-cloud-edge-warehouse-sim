# Multimodal Management System

#### 介绍
离散式生产企业仓库物料管理系统，基于 RuoYi-Vue 改造，包含物料、BOM、库存、MPS、采购/到货/销售/退货管理，以及 MRP、混合算法和订货点参数维护。

当前主项目在 `mms` 目录中：

- 后端：`mms/ruoyi-admin`
- 前端：`mms/ruoyi-ui`
- 数据库初始化：`mms/sql/ry_20231130.sql`、`mms/sql/quartz.sql`、`mms/sql/mms_business.sql`

`Algorithm/algorithm` 保留了早期独立算法服务代码；系统运行入口已整合进 `mms/ruoyi-admin`。

#### 软件架构
软件架构说明


#### 安装教程

1.  创建 MySQL 数据库并依次执行 `mms/sql/ry_20231130.sql`、`mms/sql/quartz.sql`、`mms/sql/mms_business.sql`。
2.  修改 `mms/ruoyi-admin/src/main/resources/application-druid.yml` 中的数据库连接信息。
3.  启动 Redis。
4.  启动后端 `mms/ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java`。
5.  在 `mms/ruoyi-ui` 中安装依赖并运行前端。

#### 使用说明

1.  维护物料、单位、库存、BOM 和 MPS 主生产计划。
2.  在“主生产计划”页面点击“运算”，系统会执行 BOM 分解并按 CGC 分类运行 MRP、混合算法或订货点法。
3.  在“算法管理/物料需求计划”中查看建议采购量、建议采购日期和需人工调度的异常状态。

#### 参与贡献
