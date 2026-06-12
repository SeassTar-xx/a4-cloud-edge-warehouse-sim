<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="daterangeOrderTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="商品编号" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入商品编号"
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
      <el-form-item label="采购数量" prop="purchaseVolume">
        <el-input
          v-model="queryParams.purchaseVolume"
          placeholder="请输入采购数量"
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
      <el-form-item label="预计到达时间" prop="arrivalTime">
        <el-date-picker clearable
                        v-model="queryParams.arrivalTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择预计到达时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="采购单编码" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入采购单编码"
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
          v-hasPermi="['purchaseorder:purchaseorder:add']"
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
          v-hasPermi="['purchaseorder:purchaseorder:edit']"
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
          v-hasPermi="['purchaseorder:purchaseorder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchaseorder:purchaseorder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseorderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购日期" align="center" prop="orderTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="商品编号" align="center" prop="itemCode" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="采购数量" align="center" prop="purchaseVolume" />
      <el-table-column label="单位码" align="center" prop="uid" />
      <el-table-column label="入库状态" align="center" prop="status" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="预计到达时间" align="center" prop="arrivalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购单编码" align="center" prop="orderCode" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['purchaseorder:purchaseorder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['purchaseorder:purchaseorder:remove']"
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购日期" prop="orderTime">
          <el-date-picker clearable
                          v-model="form.orderTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择采购日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="商品编号" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入商品编号" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="采购数量" prop="purchaseVolume">
          <el-input v-model="form.purchaseVolume" placeholder="请输入采购数量" />
        </el-form-item>
        <el-form-item label="单位码" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入单位码" />
        </el-form-item>
        <el-form-item label="预计到达时间" prop="arrivalTime">
          <el-date-picker clearable
                          v-model="form.arrivalTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择预计到达时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采购单编码" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入采购单编码" />
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
import { listPurchaseorder, getPurchaseorder, delPurchaseorder, addPurchaseorder, updatePurchaseorder } from "@/api/purchaseorder/purchaseorder";

export default {
  name: "Purchaseorder",
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
      // 采购订单表格数据
      purchaseorderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 采购单编码时间范围
      daterangeOrderTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderTime: null,
        itemCode: null,
        name: null,
        purchaseVolume: null,
        uid: null,
        status: null,
        arrivalTime: null,
        orderCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderTime: [
          { required: true, message: "采购日期不能为空", trigger: "blur" }
        ],
        itemCode: [
          { required: true, message: "商品编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        purchaseVolume: [
          { required: true, message: "采购数量不能为空", trigger: "blur" }
        ],
        uid: [
          { required: true, message: "单位码不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "入库状态不能为空", trigger: "change" }
        ],
        arrivalTime: [
          { required: true, message: "预计到达时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOrderTime && '' != this.daterangeOrderTime) {
        this.queryParams.params["beginOrderTime"] = this.daterangeOrderTime[0];
        this.queryParams.params["endOrderTime"] = this.daterangeOrderTime[1];
      }
      listPurchaseorder(this.queryParams).then(response => {
        this.purchaseorderList = response.rows;
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
        orderTime: null,
        itemCode: null,
        name: null,
        purchaseVolume: null,
        uid: null,
        status: null,
        id: null,
        arrivalTime: null,
        orderCode: null
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
      this.daterangeOrderTime = [];
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
      this.title = "添加采购订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPurchaseorder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchaseorder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购订单编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseorder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('purchaseorder/purchaseorder/export', {
        ...this.queryParams
      }, `purchaseorder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
