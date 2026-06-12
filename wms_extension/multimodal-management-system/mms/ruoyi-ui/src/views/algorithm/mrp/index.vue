<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="物料编码" prop="itemCode">
        <el-input v-model="queryParams.itemCode" placeholder="请输入物料编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="来源MPS" prop="mrpid">
        <el-input v-model="queryParams.mrpid" placeholder="请输入来源MPS编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="statusCode">
        <el-select v-model="queryParams.statusCode" placeholder="请选择状态" clearable>
          <el-option label="正常" :value="0" />
          <el-option label="需调度" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['algorithm:mrp:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['algorithm:mrp:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['algorithm:mrp:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['algorithm:mrp:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mrpList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="来源MPS" align="center" prop="mrpid" min-width="140" />
      <el-table-column label="物料编码" align="center" prop="itemCode" min-width="120" />
      <el-table-column label="物料名称" align="center" prop="itemName" min-width="120" />
      <el-table-column label="需求数量" align="center" prop="demandAmount" />
      <el-table-column label="建议采购量" align="center" prop="planedAmount" />
      <el-table-column label="单位" align="center" prop="unit" width="80" />
      <el-table-column label="需求日期" align="center" prop="demandTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.demandTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="建议采购日期" align="center" prop="orderTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="statusCode" width="90">
        <template slot-scope="scope">
          <el-tag :type="scope.row.statusCode === 1 ? 'danger' : 'success'">
            {{ scope.row.statusCode === 1 ? '需调度' : '正常' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="130">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['algorithm:mrp:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['algorithm:mrp:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="来源MPS" prop="mrpid">
          <el-input v-model="form.mrpid" placeholder="请输入来源MPS编码" />
        </el-form-item>
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="需求数量" prop="demandAmount">
          <el-input-number v-model="form.demandAmount" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="建议采购量" prop="planedAmount">
          <el-input-number v-model="form.planedAmount" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="需求日期" prop="demandTime">
          <el-date-picker clearable v-model="form.demandTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择需求日期" />
        </el-form-item>
        <el-form-item label="建议采购日期" prop="orderTime">
          <el-date-picker clearable v-model="form.orderTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择建议采购日期" />
        </el-form-item>
        <el-form-item label="状态" prop="statusCode">
          <el-select v-model="form.statusCode" placeholder="请选择状态">
            <el-option label="正常" :value="0" />
            <el-option label="需调度" :value="1" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMrp, getMrp, delMrp, addMrp, updateMrp } from "@/api/algorithm/mrp";

export default {
  name: "Mrp",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      mrpList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: null,
        mrpid: null,
        statusCode: null
      },
      form: {},
      rules: {
        itemCode: [{ required: true, message: "物料编码不能为空", trigger: "blur" }],
        demandTime: [{ required: true, message: "需求日期不能为空", trigger: "blur" }],
        demandAmount: [{ required: true, message: "需求数量不能为空", trigger: "blur" }],
        planedAmount: [{ required: true, message: "建议采购量不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listMrp(this.queryParams).then(response => {
        this.mrpList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        itemCode: null,
        planedAmount: 0,
        orderTime: null,
        demandTime: null,
        demandAmount: 0,
        itemName: null,
        unit: null,
        mrpid: null,
        statusCode: 0
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增物料需求计划";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMrp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料需求计划";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMrp(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMrp(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除物料需求计划编号为"' + ids + '"的数据项？').then(function() {
        return delMrp(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleExport() {
      this.download('algorithm/mrp/export', {
        ...this.queryParams
      }, `mrp_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
