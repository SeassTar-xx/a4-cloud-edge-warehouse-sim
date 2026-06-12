<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="入库日期" prop="arriveTime">
        <el-date-picker clearable
                        v-model="queryParams.arriveTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择入库日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="商品编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入商品编码"
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
      <el-form-item label="入库数量" prop="arriveNum">
        <el-input
          v-model="queryParams.arriveNum"
          placeholder="请输入入库数量"
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
      <el-form-item label="入库编码" prop="arriveCode">
        <el-input
          v-model="queryParams.arriveCode"
          placeholder="请输入入库编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联采购订单号" prop="purchaseCode">
        <el-input
          v-model="queryParams.purchaseCode"
          placeholder="请输入关联采购订单号"
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
          v-hasPermi="['arriveorder:arriveorder:add']"
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
          v-hasPermi="['arriveorder:arriveorder:edit']"
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
          v-hasPermi="['arriveorder:arriveorder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['arriveorder:arriveorder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="arriveorderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库日期" align="center" prop="arriveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arriveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="商品编码" align="center" prop="itemCode" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="入库数量" align="center" prop="arriveNum" />
      <el-table-column label="单位码" align="center" prop="uid" />
      <el-table-column label="入库编码" align="center" prop="arriveCode" />
      <el-table-column label="关联采购订单号" align="center" prop="purchaseCode" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['arriveorder:arriveorder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['arriveorder:arriveorder:remove']"
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

    <!-- 添加或修改采购入库表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="入库日期" prop="arriveTime">
          <el-date-picker clearable
                          v-model="form.arriveTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择入库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="商品编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入商品编码" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="入库数量" prop="arriveNum">
          <el-input v-model="form.arriveNum" placeholder="请输入入库数量" />
        </el-form-item>
        <el-form-item label="单位码" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入单位码" />
        </el-form-item>
        <el-form-item label="入库编码" prop="arriveCode">
          <el-input v-model="form.arriveCode" placeholder="请输入入库编码" />
        </el-form-item>
        <el-form-item label="关联采购订单号" prop="purchaseCode">
          <el-input v-model="form.purchaseCode" placeholder="请输入关联采购订单号" />
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
import { listArriveorder, getArriveorder, delArriveorder, addArriveorder, updateArriveorder } from "@/api/arriveorder/arriveorder";

export default {
  name: "Arriveorder",
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
      // 采购入库表表格数据
      arriveorderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arriveTime: null,
        itemCode: null,
        name: null,
        arriveNum: null,
        uid: null,
        arriveCode: null,
        purchaseCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        arriveTime: [
          { required: true, message: "入库日期不能为空", trigger: "blur" }
        ],
        itemCode: [
          { required: true, message: "商品编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        arriveNum: [
          { required: true, message: "入库数量不能为空", trigger: "blur" }
        ],
        uid: [
          { required: true, message: "单位码不能为空", trigger: "blur" }
        ],
        purchaseCode: [
          { required: true, message: "关联采购订单号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购入库表列表 */
    getList() {
      this.loading = true;
      listArriveorder(this.queryParams).then(response => {
        this.arriveorderList = response.rows;
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
        arriveTime: null,
        itemCode: null,
        name: null,
        arriveNum: null,
        uid: null,
        arriveCode: null,
        purchaseCode: null,
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
      this.title = "添加采购入库表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getArriveorder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购入库表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArriveorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArriveorder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购入库表编号为"' + ids + '"的数据项？').then(function() {
        return delArriveorder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('arriveorder/arriveorder/export', {
        ...this.queryParams
      }, `arriveorder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
