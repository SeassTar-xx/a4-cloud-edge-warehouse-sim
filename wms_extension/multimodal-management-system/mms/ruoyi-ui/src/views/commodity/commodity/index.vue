<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商品名称"
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
      <el-form-item label="提前期" prop="leadTime">
        <el-input
          v-model="queryParams.leadTime"
          placeholder="请输入提前期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间单位" prop="timeUid">
        <el-input
          v-model="queryParams.timeUid"
          placeholder="请输入时间单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="CGC" prop="cgc">
        <el-input
          v-model="queryParams.cgc"
          placeholder="请输入CGC"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否考虑安全库存" prop="safeFlag">
        <el-input
          v-model="queryParams.safeFlag"
          placeholder="请输入是否考虑安全库存"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="安全库存" prop="safeStoke">
        <el-input
          v-model="queryParams.safeStoke"
          placeholder="请输入安全库存"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最大库存量" prop="maxStoke">
        <el-input
          v-model="queryParams.maxStoke"
          placeholder="请输入最大库存量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预计销量" prop="expectSale">
        <el-input
          v-model="queryParams.expectSale"
          placeholder="请输入预计销量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销量单位" prop="saleUid">
        <el-input
          v-model="queryParams.saleUid"
          placeholder="请输入销量单位"
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
          v-hasPermi="['commodity:commodity:add']"
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
          v-hasPermi="['commodity:commodity:edit']"
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
          v-hasPermi="['commodity:commodity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['commodity:commodity:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commodityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="itemCode" />
      <el-table-column label="商品名称" align="center" prop="name" />
      <el-table-column label="单位码" align="center" prop="uid" />
      <el-table-column label="提前期" align="center" prop="leadTime" />
      <el-table-column label="时间单位" align="center" prop="timeUid" />
      <el-table-column label="CGC" align="center" prop="cgc" />
      <el-table-column label="是否考虑安全库存" align="center" prop="safeFlag" />
      <el-table-column label="安全库存" align="center" prop="safeStoke" />
      <el-table-column label="最大库存量" align="center" prop="maxStoke" />
      <el-table-column label="预计销量" align="center" prop="expectSale" />
      <el-table-column label="销量单位" align="center" prop="saleUid" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['commodity:commodity:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['commodity:commodity:remove']"
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

    <!-- 添加或修改商品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="单位码" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入单位码" />
        </el-form-item>
        <el-form-item label="提前期" prop="leadTime">
          <el-input v-model="form.leadTime" placeholder="请输入提前期" />
        </el-form-item>
        <el-form-item label="时间单位" prop="timeUid">
          <el-input v-model="form.timeUid" placeholder="请输入时间单位" />
        </el-form-item>
        <el-form-item label="CGC" prop="cgc">
          <el-input v-model="form.cgc" placeholder="请输入CGC" />
        </el-form-item>
        <el-form-item label="是否考虑安全库存" prop="safeFlag">
          <el-input v-model="form.safeFlag" placeholder="请输入是否考虑安全库存" />
        </el-form-item>
        <el-form-item label="安全库存" prop="safeStoke">
          <el-input v-model="form.safeStoke" placeholder="请输入安全库存" />
        </el-form-item>
        <el-form-item label="最大库存量" prop="maxStoke">
          <el-input v-model="form.maxStoke" placeholder="请输入最大库存量" />
        </el-form-item>
        <el-form-item label="预计销量" prop="expectSale">
          <el-input v-model="form.expectSale" placeholder="请输入预计销量" />
        </el-form-item>
        <el-form-item label="销量单位" prop="saleUid">
          <el-input v-model="form.saleUid" placeholder="请输入销量单位" />
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
import { listCommodity, getCommodity, delCommodity, addCommodity, updateCommodity } from "@/api/commodity/commodity";

export default {
  name: "Commodity",
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
      // 商品信息表格数据
      commodityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: null,
        name: null,
        uid: null,
        leadTime: null,
        timeUid: null,
        cgc: null,
        safeFlag: null,
        safeStoke: null,
        maxStoke: null,
        expectSale: null,
        saleUid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],
        uid: [
          { required: true, message: "单位码不能为空", trigger: "blur" }
        ],
        leadTime: [
          { required: true, message: "提前期不能为空", trigger: "blur" }
        ],
        timeUid: [
          { required: true, message: "时间单位不能为空", trigger: "blur" }
        ],
        cgc: [
          { required: true, message: "CGC不能为空", trigger: "blur" }
        ],
        safeFlag: [
          { required: true, message: "是否考虑安全库存不能为空", trigger: "blur" }
        ],
        maxStoke: [
          { required: true, message: "最大库存量不能为空", trigger: "blur" }
        ],
        expectSale: [
          { required: true, message: "预计销量不能为空", trigger: "blur" }
        ],
        saleUid: [
          { required: true, message: "销量单位不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listCommodity(this.queryParams).then(response => {
        this.commodityList = response.rows;
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
        itemCode: null,
        name: null,
        uid: null,
        leadTime: null,
        timeUid: null,
        cgc: null,
        safeFlag: null,
        safeStoke: null,
        maxStoke: null,
        expectSale: null,
        saleUid: null,
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
      this.title = "添加商品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCommodity(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCommodity(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCommodity(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品信息编号为"' + ids + '"的数据项？').then(function() {
        return delCommodity(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('commodity/commodity/export', {
        ...this.queryParams
      }, `commodity_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
