<template>
  <div class="dashboard app-container">
    <el-row :gutter="16">
      <el-col :xs="24" :sm="12" :lg="6" v-for="item in metrics" :key="item.label">
        <div class="metric-panel">
          <div class="metric-icon" :class="item.type">
            <i :class="item.icon"></i>
          </div>
          <div>
            <div class="metric-value">{{ item.value }}</div>
            <div class="metric-label">{{ item.label }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :xs="24" :lg="15">
        <div class="work-panel">
          <div class="panel-title">物料计划流程</div>
          <el-steps :active="4" finish-status="success" align-center>
            <el-step title="物料信息" />
            <el-step title="BOM维护" />
            <el-step title="MPS录入" />
            <el-step title="算法运算" />
            <el-step title="MRP报表" />
          </el-steps>
          <div class="quick-actions">
            <el-button type="primary" icon="el-icon-date" @click="go('/material/mps')">主生产计划</el-button>
            <el-button icon="el-icon-s-operation" @click="go('/algorithm/mrp')">物料需求计划</el-button>
            <el-button icon="el-icon-box" @click="go('/material/stock')">库存管理</el-button>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :lg="9">
        <div class="work-panel">
          <div class="panel-title">算法策略</div>
          <div class="strategy-row">
            <span class="strategy-badge a">A</span>
            <span>严格按 MRP 生成采购建议</span>
          </div>
          <div class="strategy-row">
            <span class="strategy-badge b">B</span>
            <span>MRP 结果结合平均可用率修正</span>
          </div>
          <div class="strategy-row">
            <span class="strategy-badge c1">C1</span>
            <span>混合策略，异常结果标记为需调度</span>
          </div>
          <div class="strategy-row">
            <span class="strategy-badge c2">C2</span>
            <span>按订货点与最大库存点补货</span>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :xs="24" :lg="12">
        <div class="work-panel">
          <div class="panel-title">核心输入</div>
          <el-table :data="inputs" size="small">
            <el-table-column label="模块" prop="name" width="120" />
            <el-table-column label="作用" prop="desc" />
          </el-table>
        </div>
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="work-panel">
          <div class="panel-title">核心输出</div>
          <el-table :data="outputs" size="small">
            <el-table-column label="报表" prop="name" width="140" />
            <el-table-column label="内容" prop="desc" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      metrics: [
        { label: "物料基础档案", value: "CGC", icon: "el-icon-collection", type: "green" },
        { label: "主生产计划", value: "MPS", icon: "el-icon-date", type: "blue" },
        { label: "物料需求计划", value: "MRP", icon: "el-icon-s-data", type: "orange" },
        { label: "订货点策略", value: "OPM", icon: "el-icon-aim", type: "purple" }
      ],
      inputs: [
        { name: "物料信息", desc: "维护提前期、安全库存、最大库存、CGC分类和单位。" },
        { name: "BOM物料清单", desc: "维护父子件结构、数量关系和节点组装时间。" },
        { name: "MPS", desc: "录入生产计划、需求数量、需求日期和关联BOM。" },
        { name: "库存/采购", desc: "提供现有库存、已分配量和在途采购订单。" }
      ],
      outputs: [
        { name: "物料需求计划", desc: "输出建议采购量、建议采购日期、来源MPS和异常状态。" },
        { name: "采购在途订单", desc: "记录未到货采购订单，参与有效库存计算。" },
        { name: "库存报表", desc: "维护库存量、已分配量、金额和物料基础信息。" },
        { name: "算法参数", desc: "维护MRP平均可用率、订货点和系统周期参数。" }
      ]
    };
  },
  methods: {
    go(path) {
      this.$router.push(path);
    }
  }
};
</script>

<style lang="scss" scoped>
.dashboard {
  background: #f4f6f8;
  min-height: calc(100vh - 84px);
}

.mt16 {
  margin-top: 16px;
}

.metric-panel,
.work-panel {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
}

.metric-panel {
  height: 104px;
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 18px;
}

.metric-icon {
  width: 48px;
  height: 48px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;

  &.green { background: #0f766e; }
  &.blue { background: #2563eb; }
  &.orange { background: #d97706; }
  &.purple { background: #7c3aed; }
}

.metric-value {
  color: #111827;
  font-size: 24px;
  font-weight: 700;
  line-height: 1.2;
}

.metric-label {
  margin-top: 6px;
  color: #6b7280;
  font-size: 13px;
}

.work-panel {
  padding: 18px;
}

.panel-title {
  color: #111827;
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 18px;
}

.quick-actions {
  margin-top: 28px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.strategy-row {
  min-height: 42px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-bottom: 1px solid #eef0f3;
  color: #374151;

  &:last-child {
    border-bottom: 0;
  }
}

.strategy-badge {
  width: 36px;
  height: 24px;
  border-radius: 4px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;

  &.a { background: #0f766e; }
  &.b { background: #2563eb; }
  &.c1 { background: #d97706; }
  &.c2 { background: #7c3aed; }
}
</style>
