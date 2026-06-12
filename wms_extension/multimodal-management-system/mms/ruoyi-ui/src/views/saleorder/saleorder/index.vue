<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="销售日期" prop="saleDate">
        <el-date-picker clearable
          v-model="queryParams.saleDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择销售日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="产品编号" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入产品编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划销售量" prop="planMount">
        <el-input
          v-model="queryParams.planMount"
          placeholder="请输入计划销售量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位码" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="请输入单位码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联BOM编码" prop="bomCode">
        <el-input
          v-model="queryParams.bomCode"
          placeholder="请输入关联BOM编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售单编码" prop="saleCode">
        <el-input
          v-model="queryParams.saleCode"
          placeholder="请输入销售单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['saleorder:saleorder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['saleorder:saleorder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['saleorder:saleorder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['saleorder:saleorder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="saleorderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="销售日期" align="center" prop="saleDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.saleDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品编号" align="center" prop="itemCode" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="计划销售量" align="center" prop="planMount" />
      <el-table-column label="单位码" align="center" prop="uid" />
      <el-table-column label="关联BOM编码" align="center" prop="bomCode" />
      <el-table-column label="销售单编码" align="center" prop="saleCode" />
      <el-table-column label="" align="center" prop="id" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['saleorder:saleorder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['saleorder:saleorder:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="销售日期" prop="saleDate">
          <el-date-picker clearable
            v-model="form.saleDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择销售日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="产品编号" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入产品编号" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="计划销售量" prop="planMount">
          <el-input v-model="form.planMount" placeholder="请输入计划销售量" />
        </el-form-item>
        <el-form-item label="单位码" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入单位码" />
        </el-form-item>
        <el-form-item label="关联BOM编码" prop="bomCode">
          <el-input v-model="form.bomCode" placeholder="请输入关联BOM编码" />
        </el-form-item>
        <el-form-item label="销售单编码" prop="saleCode">
          <el-input v-model="form.saleCode" placeholder="请输入销售单编码" />
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
import { listSaleorder, getSaleorder, delSaleorder, addSaleorder, updateSaleorder } from "@/api/saleorder/saleorder";

export default {
  name: "Saleorder",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售订单表格数据
      saleorderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        saleDate: null,
        itemCode: null,
        name: null,
        planMount: null,
        uid: null,
        bomCode: null,
        saleCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        saleDate: [
          { required: true, message: "销售日期不能为空", trigger: "blur" }
        ],
        itemCode: [
          { required: true, message: "产品编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        planMount: [
          { required: true, message: "计划销售量不能为空", trigger: "blur" }
        ],
        uid: [
          { required: true, message: "单位码不能为空", trigger: "blur" }
        ],
        bomCode: [
          { required: true, message: "关联BOM编码不能为空", trigger: "blur" }
        ],
        saleCode: [
          { required: true, message: "销售单编码不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询销售订单列表 */
    getList() {
      this.loading = true;
      listSaleorder(this.queryParams).then(response => {
        this.saleorderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        saleDate: null,
        itemCode: null,
        name: null,
        planMount: null,
        uid: null,
        bomCode: null,
        saleCode: null,
        id: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSaleorder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改销售订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSaleorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSaleorder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除销售订单编号为"' + ids + '"的数据项？').then(function() {
        return delSaleorder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('saleorder/saleorder/export', {
        ...this.queryParams
      }, `saleorder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
