<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="物料编码" prop="itemCode">
        <el-input v-model="queryParams.itemCode" placeholder="请输入物料编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="平均日用量" prop="dailyUsage">
        <el-input v-model="queryParams.dailyUsage" placeholder="请输入平均日用量" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订货点" prop="orderPoint">
        <el-input v-model="queryParams.orderPoint" placeholder="请输入订货点" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['algorithm:opminfo:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['algorithm:opminfo:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['algorithm:opminfo:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['algorithm:opminfo:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="opminfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="itemCode" />
      <el-table-column label="平均日用量" align="center" prop="dailyUsage" />
      <el-table-column label="订货点" align="center" prop="orderPoint" />
      <el-table-column label="最大库存点" align="center" prop="maxPoint" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="130">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['algorithm:opminfo:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['algorithm:opminfo:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="520px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" :disabled="formEditing" />
        </el-form-item>
        <el-form-item label="平均日用量" prop="dailyUsage">
          <el-input-number v-model="form.dailyUsage" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="订货点" prop="orderPoint">
          <el-input-number v-model="form.orderPoint" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="最大库存点" prop="maxPoint">
          <el-input-number v-model="form.maxPoint" :min="0" controls-position="right" />
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
import { listOpminfo, getOpminfo, delOpminfo, addOpminfo, updateOpminfo } from "@/api/algorithm/opminfo";

export default {
  name: "Opminfo",
  data() {
    return {
      loading: true,
      itemCodes: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      opminfoList: [],
      title: "",
      open: false,
      formEditing: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: null,
        dailyUsage: null,
        orderPoint: null,
        maxPoint: null
      },
      form: {},
      rules: {
        itemCode: [{ required: true, message: "物料编码不能为空", trigger: "blur" }],
        dailyUsage: [{ required: true, message: "平均日用量不能为空", trigger: "blur" }],
        orderPoint: [{ required: true, message: "订货点不能为空", trigger: "blur" }],
        maxPoint: [{ required: true, message: "最大库存点不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listOpminfo(this.queryParams).then(response => {
        this.opminfoList = response.rows;
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
        itemCode: null,
        dailyUsage: 0,
        orderPoint: 0,
        maxPoint: 0
      };
      this.formEditing = false;
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
      this.itemCodes = selection.map(item => item.itemCode);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增订货点参数";
    },
    handleUpdate(row) {
      this.reset();
      const itemCode = row.itemCode || this.itemCodes;
      getOpminfo(itemCode).then(response => {
        this.form = response.data;
        this.formEditing = true;
        this.open = true;
        this.title = "修改订货点参数";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.formEditing) {
            updateOpminfo(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOpminfo(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const itemCodes = row.itemCode || this.itemCodes;
      this.$modal.confirm('是否确认删除物料编码为"' + itemCodes + '"的数据项？').then(function() {
        return delOpminfo(itemCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleExport() {
      this.download('algorithm/opminfo/export', {
        ...this.queryParams
      }, `opminfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
