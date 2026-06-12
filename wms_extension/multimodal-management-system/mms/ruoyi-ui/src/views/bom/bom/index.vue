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
      <el-form-item label="父物料编码" prop="faItemCode">
        <el-input
          v-model="queryParams.faItemCode"
          placeholder="请输入父物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数量关系" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入数量关系"
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
          v-hasPermi="['bom:bom:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="bomList"
      row-key="itemCode"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="物料编码" align="center" prop="itemCode" />
      <el-table-column label="父物料编码" prop="faItemCode" />
      <el-table-column label="数量关系" align="center" prop="num" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bom:bom:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['bom:bom:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bom:bom:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改BOM对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="父物料编码" prop="faItemCode">
          <treeselect v-model="form.faItemCode" :options="bomOptions" :normalizer="normalizer" placeholder="请选择父物料编码" />
        </el-form-item>
        <el-form-item label="数量关系" prop="num">
          <el-input v-model="form.num" placeholder="请输入数量关系" />
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
import { listBom, getBom, delBom, addBom, updateBom } from "@/api/bom/bom";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Bom",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // BOM表格数据
      bomList: [],
      // BOM树选项
      bomOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        itemCode: null,
        faItemCode: null,
        num: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        faItemCode: [
          { required: true, message: "父物料编码不能为空", trigger: "blur" }
        ],
        num: [
          { required: true, message: "数量关系不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询BOM列表 */
    getList() {
      this.loading = true;
      listBom(this.queryParams).then(response => {
        this.bomList = this.handleTree(response.data, "itemCode", "faItemCode");
        this.loading = false;
      });
    },
    /** 转换BOM数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.itemCode,
        label: node.itemCode,
        children: node.children
      };
    },
	/** 查询BOM下拉树结构 */
    getTreeselect() {
      listBom().then(response => {
        this.bomOptions = [];
        const data = { itemCode: 0, itemCode: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "itemCode", "faItemCode");
        this.bomOptions.push(data);
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
        faItemCode: null,
        num: null,
        bomid: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.itemCode) {
        this.form.faItemCode = row.itemCode;
      } else {
        this.form.faItemCode = 0;
      }
      this.open = true;
      this.title = "添加BOM";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.faItemCode = row.faItemCode;
      }
      getBom(row.bomid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改BOM";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bomid != null) {
            updateBom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除BOM编号为"' + row.bomid + '"的数据项？').then(function() {
        return delBom(row.bomid);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
