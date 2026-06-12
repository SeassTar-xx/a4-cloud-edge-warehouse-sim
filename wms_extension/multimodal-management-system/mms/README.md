# 仓库物料管理系统

本目录是系统主工程，基于 Spring Boot、Vue2、Element UI 和 RuoYi-Vue 改造。

## 模块

- `ruoyi-admin`：后端入口与仓库业务、算法接口。
- `ruoyi-ui`：后台管理前端。
- `sql`：数据库初始化脚本。

## 数据库

按顺序执行：

1. `sql/ry_20231130.sql`
2. `sql/quartz.sql`
3. `sql/mms_business.sql`

业务脚本会创建物料、BOM、库存、MPS、采购、到货、销售、退货、MRP、MRP可用率、订货点和算法系统参数表，并写入系统菜单。
