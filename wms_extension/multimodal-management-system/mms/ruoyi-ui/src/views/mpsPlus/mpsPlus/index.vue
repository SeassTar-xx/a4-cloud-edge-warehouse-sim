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
      <el-form-item label="订单量" prop="demandNum">
        <el-input
          v-model="queryParams.demandNum"
          placeholder="请输入订单量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求日期" prop="demandTime">
        <el-date-picker clearable
                        v-model="queryParams.demandTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择需求日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="主生产计划表编码" prop="mpsCode">
        <el-input
          v-model="queryParams.mpsCode"
          placeholder="请输入主生产计划表编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联BOM表编码" prop="bomCode">
        <el-input
          v-model="queryParams.bomCode"
          placeholder="请输入关联BOM表编码"
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
          v-hasPermi="['mpsPlus:mps:add']"
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
          v-hasPermi="['mpsPlus:mps:edit']"
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
          v-hasPermi="['mpsPlus:mps:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mpsPlus:mps:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mpsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="itemCode" />
      <el-table-column label="订单量" align="center" prop="demandNum" />
      <el-table-column label="需求日期" align="center" prop="demandTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.demandTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="主生产计划表编码" align="center" prop="mpsCode" />
      <el-table-column label="关联BOM表编码" align="center" prop="bomCode" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            @click="handleRun(scope.row)"
            v-hasPermi="['mpsPlus:mps:run']"
          >运算</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mpsPlus:mps:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mpsPlus:mps:remove']"
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

    <!-- 添加或修改mps对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="订单量" prop="demandNum">
          <el-input v-model="form.demandNum" placeholder="请输入订单量" />
        </el-form-item>
        <el-form-item label="需求日期" prop="demandTime">
          <el-date-picker clearable
                          v-model="form.demandTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择需求日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="主生产计划表编码" prop="mpsCode">
          <el-input v-model="form.mpsCode" placeholder="请输入主生产计划表编码" />
        </el-form-item>
        <el-form-item label="关联BOM表编码" prop="bomCode">
          <el-input v-model="form.bomCode" placeholder="请输入关联BOM表编码" />
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
import { listMps, getMps, delMps, addMps, updateMps, runMps } from "@/api/mpsPlus/mps";

export default {
  name: "Mps",
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
      // mps表格数据
      mpsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemCode: null,
        demandNum: null,
        demandTime: null,
        mpsCode: null,
        bomCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        demandNum: [
          { required: true, message: "订单量不能为空", trigger: "blur" }
        ],
        demandTime: [
          { required: true, message: "需求日期不能为空", trigger: "blur" }
        ],
        bomCode: [
          { required: true, message: "关联BOM表编码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询mps列表 */
    getList() {
      this.loading = true;
      listMps(this.queryParams).then(response => {
        this.mpsList = response.rows;
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
        demandNum: null,
        demandTime: null,
        id: null,
        mpsCode: null,
        bomCode: null
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
      this.title = "添加mps";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMps(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改mps";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMps(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMps(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除mps编号为"' + ids + '"的数据项？').then(function() {
        return delMps(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mpsPlus/mps/export', {
        ...this.queryParams
      }, `mps_${new Date().getTime()}.xlsx`)
    },
    /** 运行算法 */
    handleRun(row) {
      this.$modal.confirm('是否运行主生产计划"' + row.mpsCode + '"并生成物料需求计划？').then(function() {
        return runMps(row.id);
      }).then(response => {
        this.$modal.msgSuccess(response.msg || "算法运行完成");
        this.getList();
      }).catch(() => {});
    }
  }
};
</script>
