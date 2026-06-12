<template>
  <div>
    <!-- 外部盒子嵌套 -->
    <el-container>
      <!-- 侧边栏  -->
      <el-aside
        :width="asideWidth"
        style="min-height: 100vh; background-color: #001529"
      >
        <!-- 系统名称 -->
        <!-- <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">honey2024</span>
        </div> -->
        <!-- 菜单 -->
        <!-- collape水平折叠菜单 -->
        <el-menu
          :collapse="isCollapse"
          :collapse-transition="false"
          router
          background-color="#001529"
          text-color="rgba(255, 255, 255, 0.65)"
          active-text-color="#fff"
          style="border: none"
          :default-active="$route.path"
        >
          <!-- 二级菜单 -->
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>生产</span>
            </template>
            <el-menu-item index="1_1">主生产计划表</el-menu-item>
            <el-menu-item index="1_2">主生产计划列表</el-menu-item>
            <el-menu-item index="1_3">物料需求计划表</el-menu-item>
            <el-menu-item index="1_4">物料需求计划列表</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>采购</span>
            </template>
            <el-menu-item index="2_1">采购订单</el-menu-item>
            <el-menu-item index="2_2">采购订单列表</el-menu-item>
            <el-menu-item index="2_3">采购入库单</el-menu-item>
            <el-menu-item index="2_4">采购入库单列表</el-menu-item>
            <el-menu-item index="2_5">采购退货单</el-menu-item>
            <el-menu-item index="2_6">采购退货单列表</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>销售</span>
            </template>
            <el-menu-item index="3_1">销售订单</el-menu-item>
            <el-menu-item index="3_2">销售订单列表</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>库存</span>
            </template>
            <el-menu-item index="4_1">新增BOM表</el-menu-item>
            <el-menu-item index="4_2">BOM列表</el-menu-item>
            <el-menu-item index="4_3">库存余额表</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>资料</span>
            </template>
            <el-menu-item index="5_1">单位</el-menu-item>
            <el-menu-item index="5_2">商品</el-menu-item>
            <el-menu-item index="5_3">初期库存录入</el-menu-item>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>设置</span>
            </template>
            <el-menu-item index="6_1">编辑个人信息</el-menu-item>
            <el-menu-item index="/homePage">首页</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 内容区域 -->
      <el-container>
        <!--头部区域-->
        <el-header>
          <!-- icon绑定 -->
          <i
            :class="collapseIcon"
            style="font-size: 26px"
            @click="handleCollapse"
          ></i>
          <el-breadcrumb
            separator-class="el-icon-arrow-right"
            style="margin-left: 20px"
          >
            <el-breadcrumb-item :to="{ path: '/homePage' }"
              >资料</el-breadcrumb-item
            >
            <el-breadcrumb-item :to="{ path: '/5_1' }">单位</el-breadcrumb-item>
          </el-breadcrumb>
          <div
            style="
              flex: 1;
              width: 0;
              display: flex;
              align-items: center;
              justify-content: flex-end;
            "
          >
            <i
              class="el-icon-quanping"
              style="font-size: 26px"
              @click="handleFull"
            ></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img
                  src="@/assets/logo1.png"
                  alt=""
                  style="width: 40px; height: 40px; margin: 0 5px"
                />
                <span>个人信息</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>编辑个人信息</el-dropdown-item>
                <el-dropdown-item @click.prevent="noop"
                  >退出登录</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <!--主体区域-->
        <el-main>
          <el-button type="primary" plain @click="showDialog">新增</el-button>
          <hr />
          <!-- 单位表格 -->
          <el-table
            :data="unitList"
            style="width: 100%"
            border
            :header-cell-style="{ background: 'aliceblue', fontSize: '16px' }"
          >
            <el-table-column
              prop="name"
              label="单位名称"
              align="center"
            ></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  @click="handleEdit(scope.$index, scope.row)"
                  >编辑</el-button
                >
                <el-button
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 新增对话框 -->
          <el-dialog
            title="新增单位"
            :visible.sync="dialogVisible"
            @close="handleClose"
            width="30%"
          >
            <el-input
              v-model="newUnitName"
              placeholder="请输入单位名称"
            ></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="handleClose">取消</el-button>
              <el-button type="primary" @click="handleAdd">确定</el-button>
            </span>
          </el-dialog>
          <!-- 编辑对话框 -->
          <el-dialog
            title="编辑单位"
            :visible.sync="editDialogVisible"
            @close="handleEditClose"
            width="30%"
          >
            <el-input
              v-model="editUnitName"
              placeholder="请输入单位名称"
            ></el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="handleEditClose">取消</el-button>
              <el-button type="primary" @click="handleEditSave">保存</el-button>
            </span>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// 导入 Axios
import axios from 'axios';

export default {
  name: "unit",
  data() {
    return {
      isCollapse: false, // 不收缩
      asideWidth: "200px",
      collapseIcon: "el-icon-s-fold",
      unitList: [], // 单位名称列表
      dialogVisible: false, // 对话框不可见
      newUnitName: "", // 新增的单位名称
      editDialogVisible: false, // 编辑单位对话框可见状态
      editRowIndex: null, // 正在编辑的行索引
      editUnitName: "", // 编辑的单位名称
    };
  },
  methods: {
    handleFull() {
      document.documentElement.requestFullscreen();
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? "64px" : "200px";
      this.collapseIcon = this.isCollapse
        ? "el-icon-s-unfold"
        : "el-icon-s-fold";
    },
    noop() {
      // 什么也不做
    },
    // 点击“新增”按钮，显示对话框
    showDialog() {
      this.dialogVisible = true;
    },
    // 关闭对话框
    handleClose() {
      this.dialogVisible = false;
      this.newUnitName = "";
    },
    // 处理新增单位操作
    handleAdd() {
      // 将新增单位名称保存到数据库，这里假设已经有一个API可以实现保存操作
      // 这里假设保存成功后，返回一个包含新增单位的对象
      const newUnit = { name: this.newUnitName };
      // 发送数据到后端保存
      axios
        .post("/saveData", newUnit)
        .then((response) => {
          // 处理保存成功的逻辑
          console.log("数据保存成功");
          // 添加新增单位到列表中
          this.unitList.push(newUnit);
          // 提交成功后清空表格数据
          this.newUnitName = "";
          this.$message.success("保存成功");
          // 关闭对话框
          this.dialogVisible = false;
        })
        .catch((error) => {
          // 处理保存失败的逻辑
          console.error("数据保存失败:", error);
        });
    },
    handleEdit(index, row) {
      // 处理编辑单位操作
      this.editRowIndex = index;
      this.editUnitName = row.name;
      this.editDialogVisible = true;
    },
    handleEditClose() {
      // 关闭编辑对话框
      this.editRowIndex = null;
      this.editUnitName = "";
      this.editDialogVisible = false;
    },
    handleEditSave() {
      // 保存编辑后的单位名称
      if (this.editRowIndex !== null) {
        this.unitList[this.editRowIndex].name = this.editUnitName;
        // 更新数据库中的记录
        // axios.put("/updateData", { id: this.unitList[this.editRowIndex].id, name: this.editUnitName })
        //   .then(response => {
        //     console.log("Update successful");
        //   })
        //   .catch(error => {
        //     console.error("Update failed:", error);
        //   });
        this.editRowIndex = null;
        this.editUnitName = "";
        this.editDialogVisible = false;
        this.$message.success("保存成功");
      }
    },
    handleDelete(index, row) {
      // 处理删除单位操作
      this.$confirm("确认删除该单位信息吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 删除数据库中的记录
          // axios.delete(`/deleteData/${row.id}`)
          //   .then(response => {
          //     console.log("Delete successful");
          //     // 从前端列表中移除被删除的单位
          //     this.unitList.splice(index, 1);
          //   })
          //   .catch(error => {
          //     console.error("Delete failed:", error);
          //   });

          // 模拟删除成功后从前端列表中移除被删除的单位
          console.log("Delete successful");
          this.unitList.splice(index, 1);

          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style>
.el-menu--inline {
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover,
.el-submenu__title:hover {
  color: #fff !important;
}
.el-submenu__title:hover i {
  color: #fff !important;
}
.el-menu-item:hover i {
  color: #fff !important;
}
.el-menu-item.is-active {
  background-color: #1890ff !important;
  border-radius: 5px !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}
.el-menu-item.is-active i,
.el-menu-item.is-active .el-tooltip {
  margin-left: -4px;
}
.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu .el-menu-item {
  min-width: 0 !important;
}
.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}
/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
  transition: width 0.3s;
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all 0.3s; /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  display: flex;
  align-items: center;
}
.el-table th.is-center,
.el-table td.is-center {
  text-align: center !important;
}
</style>