-- 仓库物料管理系统业务表与菜单
-- 先执行 ry_20231130.sql 和 quartz.sql，再执行本脚本。

create table if not exists unit (
  uid bigint not null auto_increment comment '单位ID',
  unit varchar(64) not null comment '单位名称',
  primary key (uid)
) engine=innodb default charset=utf8mb4 comment='单位表';

create table if not exists commodity (
  id int not null auto_increment comment 'ID',
  itemCode varchar(64) not null comment '物料编码',
  name varchar(128) comment '商品名称',
  uid bigint comment '单位码',
  leadTime bigint default 0 comment '提前期',
  timeUid bigint comment '时间单位',
  cgc varchar(8) comment 'CGC分类',
  safeFlag bigint default 1 comment '是否考虑安全库存',
  safeStoke decimal(18,4) default 0 comment '安全库存',
  maxStoke decimal(18,4) default 0 comment '最大库存量',
  expectSale decimal(18,4) default 0 comment '预计销量',
  saleUid bigint comment '销量单位',
  primary key (id),
  unique key uk_commodity_itemCode (itemCode)
) engine=innodb default charset=utf8mb4 comment='物料基础信息';

create table if not exists stock (
  id int not null auto_increment comment 'ID',
  itemCode varchar(64) not null comment '物料编码',
  name varchar(128) comment '名称',
  price decimal(18,4) default 0 comment '价格',
  stock decimal(18,4) default 0 comment '库存',
  totalCost decimal(18,4) default 0 comment '总成本',
  assignMount decimal(18,4) default 0 comment '已分配量',
  primary key (id),
  unique key uk_stock_itemCode (itemCode)
) engine=innodb default charset=utf8mb4 comment='库存表';

create table if not exists mps (
  id int not null auto_increment comment 'ID',
  itemCode varchar(64) not null comment '物料编码',
  demandNum decimal(18,4) not null comment '订单量',
  demandTime date not null comment '需求日期',
  mpsCode varchar(64) comment '主生产计划编码',
  bomCode varchar(64) not null comment '关联BOM编码',
  primary key (id),
  key idx_mps_code (mpsCode)
) engine=innodb default charset=utf8mb4 comment='MPS主生产计划';

create table if not exists bom (
  bomCode varchar(64) not null comment 'BOM编码',
  itemCode varchar(64) not null comment '成品物料编码',
  primary key (bomCode)
) engine=innodb default charset=utf8mb4 comment='BOM头';

create table if not exists bom_details (
  bomid bigint not null auto_increment comment '节点ID',
  itemCode varchar(64) not null comment '物料编码',
  parentBomId bigint not null default 0 comment '父节点ID',
  num int default 1 comment '数量关系',
  leaderTime int default 0 comment '节点组装时间',
  bomCode varchar(64) not null comment 'BOM编码',
  primary key (bomid),
  key idx_bom_details_code (bomCode),
  key idx_bom_details_parent (parentBomId)
) engine=innodb default charset=utf8mb4 comment='BOM明细';

create table if not exists purchaseorder (
  id int not null auto_increment comment 'ID',
  orderTime date comment '采购时间',
  itemCode varchar(64) comment '物料编码',
  name varchar(128) comment '名称',
  purchaseVolume decimal(18,4) default 0 comment '采购量',
  uid int comment '单位',
  status int default 0 comment '状态',
  arrivalTime date comment '到货时间',
  orderCode varchar(64) comment '采购单号',
  primary key (id),
  key idx_purchase_item (itemCode)
) engine=innodb default charset=utf8mb4 comment='采购订单';

create table if not exists arriveorder (
  id int not null auto_increment comment 'ID',
  arriveTime date comment '到货时间',
  itemCode varchar(64) comment '物料编码',
  name varchar(128) comment '名称',
  arriveNum int default 0 comment '到货数量',
  uid int comment '单位',
  arriveCode varchar(64) comment '到货单号',
  purchaseCode varchar(64) comment '采购单号',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='到货单';

create table if not exists saleorder (
  id int not null auto_increment comment 'ID',
  saleDate date comment '销售日期',
  itemCode varchar(64) comment '物料编码',
  name varchar(128) comment '名称',
  planMount decimal(18,4) default 0 comment '计划数量',
  uid int comment '单位',
  bomCode varchar(64) comment 'BOM编码',
  saleCode varchar(64) comment '销售单号',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='销售订单';

create table if not exists returnorder (
  id bigint not null auto_increment comment 'ID',
  orderCode varchar(64) comment '订单编号',
  itemCode varchar(64) comment '物料编码',
  returnTime date comment '退货时间',
  returnNum decimal(18,4) default 0 comment '退货数量',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='退货单';

create table if not exists mrp (
  id bigint not null auto_increment comment 'ID',
  itemCode varchar(64) comment '物料编码',
  planedAmount decimal(18,4) default 0 comment '建议采购量',
  orderTime date comment '建议采购时间',
  demandTime date comment '需求时间',
  demandAmount decimal(18,4) default 0 comment '需求数量',
  itemName varchar(128) comment '物料名称',
  unit varchar(64) comment '单位',
  mrpid varchar(64) comment '来源MPS编码',
  statusCode int default 0 comment '状态码',
  primary key (id),
  key idx_mrp_mrpid (mrpid),
  key idx_mrp_item (itemCode)
) engine=innodb default charset=utf8mb4 comment='物料需求计划';

create table if not exists mrpinfo (
  itemCode varchar(64) not null comment '物料编码',
  averageAvailability decimal(18,4) default 1 comment '平均MRP可用率',
  primary key (itemCode)
) engine=innodb default charset=utf8mb4 comment='MRP可用率参数';

create table if not exists opminfo (
  itemCode varchar(64) not null comment '物料编码',
  dailyUsage decimal(18,4) default 0 comment '平均日用量',
  orderPoint decimal(18,4) default 0 comment '订货点',
  maxPoint decimal(18,4) default 0 comment '最大库存点',
  primary key (itemCode)
) engine=innodb default charset=utf8mb4 comment='订货点参数';

create table if not exists mrphistory (
  id varchar(64) not null comment 'ID',
  itemCode varchar(64) comment '物料编码',
  cycleNum bigint comment '周期号',
  MRPNum decimal(18,4) default 0 comment 'MRP运算数量',
  actualUsage decimal(18,4) default 0 comment '实际使用量',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='MRP历史';

create table if not exists opmhistory (
  id varchar(64) not null comment 'ID',
  itemCode varchar(64) comment '物料编码',
  cycleNum bigint comment '周期号',
  actualUsage decimal(18,4) default 0 comment '实际使用量',
  primary key (id)
) engine=innodb default charset=utf8mb4 comment='订货点历史';

create table if not exists systeminfo (
  currentCycleNum bigint not null comment '当前周期号',
  cycleLen bigint not null default 30 comment '周期长度',
  exceedPossible int not null default 1 comment '是否允许超出订货点最高阈值',
  lotsPrinciple int not null default 0 comment '经济批量策略',
  primary key (currentCycleNum)
) engine=innodb default charset=utf8mb4 comment='算法系统参数';

insert ignore into unit(uid, unit) values (1, '件'), (2, '箱'), (3, '天');
insert ignore into systeminfo(currentCycleNum, cycleLen, exceedPossible, lotsPrinciple) values (1, 30, 1, 0);

-- 业务菜单
insert ignore into sys_menu values(3000, '物料管理', 0, 2, 'material', null, '', 1, 0, 'M', '0', '0', '', 'tree', 'admin', sysdate(), '', null, '仓库物料业务目录');
insert ignore into sys_menu values(3001, '物料信息', 3000, 1, 'commodity', 'commodity/commodity/index', '', 1, 0, 'C', '0', '0', 'commodity:commodity:list', 'shopping', 'admin', sysdate(), '', null, '物料信息菜单');
insert ignore into sys_menu values(3002, '单位管理', 3000, 2, 'unit', 'unit/unit/index', '', 1, 0, 'C', '0', '0', 'unit:unit:list', 'dict', 'admin', sysdate(), '', null, '单位管理菜单');
insert ignore into sys_menu values(3003, 'BOM管理', 3000, 3, 'bom', 'bom/bom/index', '', 1, 0, 'C', '0', '0', 'bom:bom:list', 'tree-table', 'admin', sysdate(), '', null, 'BOM管理菜单');
insert ignore into sys_menu values(3004, '库存管理', 3000, 4, 'stock', 'stock/stock/index', '', 1, 0, 'C', '0', '0', 'stock:stock:list', 'list', 'admin', sysdate(), '', null, '库存管理菜单');
insert ignore into sys_menu values(3005, '主生产计划', 3000, 5, 'mps', 'mpsPlus/mpsPlus/index', '', 1, 0, 'C', '0', '0', 'mpsPlus:mps:list', 'date', 'admin', sysdate(), '', null, '主生产计划菜单');
insert ignore into sys_menu values(3006, '采购订单', 3000, 6, 'purchaseorder', 'purchaseorder/purchaseorder/index', '', 1, 0, 'C', '0', '0', 'purchaseorder:purchaseorder:list', 'shopping', 'admin', sysdate(), '', null, '采购订单菜单');
insert ignore into sys_menu values(3007, '到货管理', 3000, 7, 'arriveorder', 'arriveorder/arriveorder/index', '', 1, 0, 'C', '0', '0', 'arriveorder:arriveorder:list', 'logistics', 'admin', sysdate(), '', null, '到货管理菜单');
insert ignore into sys_menu values(3008, '销售订单', 3000, 8, 'saleorder', 'saleorder/saleorder/index', '', 1, 0, 'C', '0', '0', 'saleorder:saleorder:list', 'money', 'admin', sysdate(), '', null, '销售订单菜单');
insert ignore into sys_menu values(3009, '退货管理', 3000, 9, 'returnorder', 'returnorder/returnorder/index', '', 1, 0, 'C', '0', '0', 'returnorder:returnorder:list', 'rollback', 'admin', sysdate(), '', null, '退货管理菜单');

insert ignore into sys_menu values(3100, '算法管理', 0, 3, 'algorithm', null, '', 1, 0, 'M', '0', '0', '', 'chart', 'admin', sysdate(), '', null, '算法管理目录');
insert ignore into sys_menu values(3101, '物料需求计划', 3100, 1, 'mrp', 'algorithm/mrp/index', '', 1, 0, 'C', '0', '0', 'algorithm:mrp:list', 'table', 'admin', sysdate(), '', null, '物料需求计划菜单');
insert ignore into sys_menu values(3102, 'MRP可用率', 3100, 2, 'mrpinfo', 'algorithm/mrpinfo/index', '', 1, 0, 'C', '0', '0', 'algorithm:mrpinfo:list', 'number', 'admin', sysdate(), '', null, 'MRP可用率菜单');
insert ignore into sys_menu values(3103, '订货点参数', 3100, 3, 'opminfo', 'algorithm/opminfo/index', '', 1, 0, 'C', '0', '0', 'algorithm:opminfo:list', 'slider', 'admin', sysdate(), '', null, '订货点参数菜单');
insert ignore into sys_menu values(3104, '系统参数', 3100, 4, 'systeminfo', 'algorithm/systeminfo/index', '', 1, 0, 'C', '0', '0', 'algorithm:systeminfo:list', 'system', 'admin', sysdate(), '', null, '算法系统参数菜单');

-- 常用按钮权限
insert ignore into sys_menu values(30201, '物料信息查询', 3001, 1, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30202, '物料信息新增', 3001, 2, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30203, '物料信息修改', 3001, 3, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30204, '物料信息删除', 3001, 4, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30205, '物料信息导出', 3001, 5, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30206, '物料自动补全', 3001, 6, '', '', '', 1, 0, 'F', '0', '0', 'commodity:commodity:auto', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30301, '单位查询', 3002, 1, '', '', '', 1, 0, 'F', '0', '0', 'unit:unit:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30302, '单位新增', 3002, 2, '', '', '', 1, 0, 'F', '0', '0', 'unit:unit:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30303, '单位修改', 3002, 3, '', '', '', 1, 0, 'F', '0', '0', 'unit:unit:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30304, '单位删除', 3002, 4, '', '', '', 1, 0, 'F', '0', '0', 'unit:unit:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30305, '单位导出', 3002, 5, '', '', '', 1, 0, 'F', '0', '0', 'unit:unit:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30401, 'BOM查询', 3003, 1, '', '', '', 1, 0, 'F', '0', '0', 'bom:bom:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30402, 'BOM新增', 3003, 2, '', '', '', 1, 0, 'F', '0', '0', 'bom:bom:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30403, 'BOM修改', 3003, 3, '', '', '', 1, 0, 'F', '0', '0', 'bom:bom:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30404, 'BOM删除', 3003, 4, '', '', '', 1, 0, 'F', '0', '0', 'bom:bom:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30405, 'BOM导出', 3003, 5, '', '', '', 1, 0, 'F', '0', '0', 'bom:bom:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30501, '库存查询', 3004, 1, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30502, '库存新增', 3004, 2, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30503, '库存修改', 3004, 3, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30504, '库存删除', 3004, 4, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30505, '库存导出', 3004, 5, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30506, '库存物料查询', 3004, 6, '', '', '', 1, 0, 'F', '0', '0', 'stock:stock:itemCode', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30101, '主生产计划运行', 3005, 1, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:run', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30100, '主生产计划查询', 3005, 0, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30102, '主生产计划新增', 3005, 2, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30103, '主生产计划修改', 3005, 3, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30104, '主生产计划删除', 3005, 4, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30105, '主生产计划导出', 3005, 5, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30106, '主生产计划编码', 3005, 6, '', '', '', 1, 0, 'F', '0', '0', 'mpsPlus:mps:mpsCode', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30601, '采购订单查询', 3006, 1, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30602, '采购订单新增', 3006, 2, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30603, '采购订单修改', 3006, 3, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30604, '采购订单删除', 3006, 4, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30605, '采购订单导出', 3006, 5, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30606, '采购订单编码', 3006, 6, '', '', '', 1, 0, 'F', '0', '0', 'purchaseorder:purchaseorder:orderCode', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30701, '到货查询', 3007, 1, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30702, '到货新增', 3007, 2, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30703, '到货修改', 3007, 3, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30704, '到货删除', 3007, 4, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30705, '到货导出', 3007, 5, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30706, '到货单编码', 3007, 6, '', '', '', 1, 0, 'F', '0', '0', 'arriveorder:arriveorder:orderCode', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30801, '销售订单查询', 3008, 1, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30802, '销售订单新增', 3008, 2, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30803, '销售订单修改', 3008, 3, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30804, '销售订单删除', 3008, 4, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30805, '销售订单导出', 3008, 5, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:export', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30806, '销售订单编码', 3008, 6, '', '', '', 1, 0, 'F', '0', '0', 'saleorder:saleorder:orderCode', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(30901, '退货查询', 3009, 1, '', '', '', 1, 0, 'F', '0', '0', 'returnorder:returnorder:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30902, '退货新增', 3009, 2, '', '', '', 1, 0, 'F', '0', '0', 'returnorder:returnorder:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30903, '退货修改', 3009, 3, '', '', '', 1, 0, 'F', '0', '0', 'returnorder:returnorder:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30904, '退货删除', 3009, 4, '', '', '', 1, 0, 'F', '0', '0', 'returnorder:returnorder:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(30905, '退货导出', 3009, 5, '', '', '', 1, 0, 'F', '0', '0', 'returnorder:returnorder:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(31101, '物料需求查询', 3101, 1, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrp:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31102, '物料需求新增', 3101, 2, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrp:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31103, '物料需求修改', 3101, 3, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrp:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31104, '物料需求删除', 3101, 4, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrp:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31105, '物料需求导出', 3101, 5, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrp:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(31201, 'MRP可用率查询', 3102, 1, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrpinfo:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31202, 'MRP可用率新增', 3102, 2, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrpinfo:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31203, 'MRP可用率修改', 3102, 3, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrpinfo:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31204, 'MRP可用率删除', 3102, 4, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrpinfo:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31205, 'MRP可用率导出', 3102, 5, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:mrpinfo:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(31301, '订货点查询', 3103, 1, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:opminfo:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31302, '订货点新增', 3103, 2, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:opminfo:add', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31303, '订货点修改', 3103, 3, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:opminfo:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31304, '订货点删除', 3103, 4, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:opminfo:remove', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31305, '订货点导出', 3103, 5, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:opminfo:export', '#', 'admin', sysdate(), '', null, '');

insert ignore into sys_menu values(31401, '算法参数查询', 3104, 1, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:systeminfo:query', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31402, '算法参数修改', 3104, 2, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:systeminfo:edit', '#', 'admin', sysdate(), '', null, '');
insert ignore into sys_menu values(31403, '算法参数导出', 3104, 3, '', '', '', 1, 0, 'F', '0', '0', 'algorithm:systeminfo:export', '#', 'admin', sysdate(), '', null, '');
