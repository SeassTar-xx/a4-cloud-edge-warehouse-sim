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
              >生产</el-breadcrumb-item
            >
            <el-breadcrumb-item :to="{ path: '/1_1' }"
              >主生产计划表</el-breadcrumb-item
            >
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
          <div
            style="
              box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
              padding: 10px 20px;
              border-radius: 5px;
              margin-bottom: 10px;
            "
          >
            <el-row>
              <!-- 保存/新增 -->
              <el-button type="success" plain @click="saveDataToDatabase"
                >保存</el-button
              >
              <el-button type="primary" plain @click="handleAdd"
                >新增</el-button
              >
              <!-- 需求日期 -->
              <span style="margin-left: 20px">需求日期：</span>
              <el-date-picker
                v-model="date"
                type="date"
                :picker-options="datePickerOptions"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
                @change="changeDate"
              ></el-date-picker>
              <!-- 弹窗 -->
              <el-dialog
                title="新增数据"
                :visible.sync="isModalVisible"
                width="30%"
              >
                <el-form label-width="120px" @submit.native.prevent="saveData">
                  <el-form-item label="商品编码">
                    <el-input
                      v-model="formData.productCode"
                      required
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="计划产量">
                    <el-input
                      v-model.number="formData.quantity"
                      required
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="关联BOM表编码">
                    <el-input v-model="formData.bomCode" required></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" native-type="submit"
                      >保存</el-button
                    >
                  </el-form-item>
                </el-form>
              </el-dialog>
            </el-row>
            <hr />
            <!-- 表格 -->
            <el-table
              :data="tableData"
              style="width: 100%"
              border
              :header-cell-style="{ background: 'aliceblue', fontSize: '16px' }"
            >
              <el-table-column
                prop="productCode"
                label="商品编码"
              ></el-table-column>
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column
                prop="quantity"
                label="计划产量"
              ></el-table-column>
              <el-table-column prop="unit" label="单位"></el-table-column>
              <el-table-column
                prop="bomCode"
                label="关联BOM表编码"
              ></el-table-column>
              <el-table-column
                prop="mpsCode"
                label="MPS编码"
                v-if="false"
              ></el-table-column>
              <!-- 不显示 -->
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="primary" @click="showForm('edit', scope.row)"
                    >修改</el-button
                  >
                  <el-button type="danger" @click="deleteItem(scope.$index)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// 导入 Axios
import axios from 'axios';

export default {
  name: "MPS",
  data() {
    return {
      isCollapse: false, // 不收缩
      asideWidth: "200px",
      collapseIcon: "el-icon-s-fold",
      date: "", // 初始化为当前日期,
      datePickerOptions: {
        disabledDate(date) {
          // 获取今天的日期
          const today = new Date();
          // 将日期设置为今天的开始，即零点
          today.setHours(0, 0, 0, 0);
          // 如果选择的日期小于今天，则禁用
          return date < today;
        },
      },
      isModalVisible: false,
      formData: {
        productCode: "",
        quantity: 0,
        bomCode: "",
      },
      tableData: [],
      tableCounts: {}, // 用于跟踪每个日期的表格数量的对象
    };
  },
  watch: {
    date(newValue) {
      if (newValue) {
        // 清空表格数据
        this.tableData = [];
      }
    },
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
    changeDate() {
      console.log(this.date);
    },

    handleAdd() {
      if (!this.date) {
        this.$message({
          type: "warning",
          message: "请选择日期",
        });
      } else {
        this.showForm("add");
      }
    },
    showForm(type, row) {
      // 点击“新增”按钮，显示弹窗
      if (type === "add") {
        this.formData.productCode = "";
        this.formData.quantity = "";
        this.formData.bomCode = "";
      } else if (type === "edit") {
        // 点击编辑按钮时，将当前行的数据赋值给表单数据
        this.formData.productCode = row.productCode;
        this.formData.quantity = row.quantity;
        this.formData.bomCode = row.bomCode;
        // 从表格数据中移除当前行
        const index = this.tableData.indexOf(row);
        if (index !== -1) {
          this.tableData.splice(index, 1);
        }
      }
      this.isModalVisible = true;
    },
    saveData() {
      // 检查商品编码格式是否正确
      const productCodeRegex = /^SP\d{3}$/; // 匹配“SP”+三位数字的正则表达式
      if (!productCodeRegex.test(this.formData.productCode)) {
        // 提示用户输入正确的格式
        this.$message({
          type: "warning",
          message: "商品编码格式不正确",
        });
        return; // 停止保存数据的操作
      }
      // 检查数量是否为数字
      if (isNaN(this.formData.quantity) || this.formData.quantity <= 0) {
        // 提示用户输入正确的数量
        this.$message({
          type: "warning",
          message: "计划产量输入不正确，必须是大于0的数字",
        });
        return; // 停止保存数据的操作
      }
      // 检查BOM表编码格式是否正确
      const bomCodeRegex = /^BOM\d{3}$/; // 匹配“SP”+三位数字的正则表达式
      if (!bomCodeRegex.test(this.formData.bomCode)) {
        // 提示用户输入正确的格式
        this.$message({
          type: "warning",
          message: "BOM表编码格式不正确",
        });
        return; // 停止保存数据的操作
      }
      // 模拟根据商品编码、关联BOM表编码从数据库中获取名称和单位
      const name = this.getNameFromDatabase(this.formData.productCode);
      const unit = this.getUnitFromDatabase(this.formData.bomCode);
      // 将数据添加到表格数据中
      this.tableData.push({
        productCode: this.formData.productCode,
        name: name,
        quantity: this.formData.quantity,
        unit: unit,
        bomCode: this.formData.bomCode,
        date: this.date,
      });

      // 清空表单数据
      this.formData.productCode = "";
      this.formData.quantity = "";
      this.formData.bomCode = "";

      // 关闭弹窗
      this.isModalVisible = false;
    },

    saveDataToDatabase() {
      if (this.tableData.length === 0) {
        this.$message({
          type: "warning",
          message: "请添加数据",
        });
      } else {
        //将日期字符转化为日期格式
        this.date = new Date(this.date);
        // 获取选择日期的年月日
        const year = this.date.getFullYear();
        const month = String(this.date.getMonth() + 1).padStart(2, "0");
        const day = String(this.date.getDate()).padStart(2, "0");

        // 构造日期字符串
        const dateString = `${year}${month}${day}`;

        // 检查日期对应的表格数量
        if (!this.tableCounts[dateString]) {
          this.tableCounts[dateString] = 0;
        }

        // 增加计数器
        this.tableCounts[dateString]++;

        // 生成表格编码
        const tableCode = `MPS${dateString}${this.padNumber(
          this.tableCounts[dateString],
          3
        )}`;

        // 将表格编码添加到每行数据的 mpsCode 属性中
        this.tableData.forEach((row) => {
          row.mpsCode = tableCode;
        });

        // 构造需要提交的数据对象
        const postData = {
          date: this.date,
          tableCode: tableCode,
          tableData: this.tableData,
        };
        // 发送数据到后端保存
        axios
          .post("/saveData", postData)
          .then((response) => {
        // 处理保存成功的逻辑
        console.log("数据保存成功");
        // 提交成功后清空表格数据和选择的日期
        this.tableData = [];
        (this.date = ""), // 初始化为当前日期;
          this.$message.success("保存成功");
        })
        .catch((error) => {
          // 处理保存失败的逻辑
          console.error("数据保存失败:", error);
        });
      }
    },
    padNumber(number, length) {
      // 辅助函数，用于将数字填充到指定长度，例如将2填充为002
      return String(number).padStart(length, "0");
    },
    // 模拟从数据库获取名称
    getNameFromDatabase(productCode) {
      // 这里可以根据商品编码查询数据库，这里简单返回一个固定值
      return "商品名称";
    },
    // 模拟从数据库获取单位
    getUnitFromDatabase(bomCode) {
      // 这里可以根据BOM表编码查询数据库，这里简单返回一个固定值
      return "个";
    },
    editItem(row) {
      // 编辑操作
      // 将当前行的数据赋值给表单数据
      this.formData.productCode = row.productCode;
      this.formData.quantity = row.quantity;
      this.formData.bomCode = row.bomCode;
      // 从表格数据中移除当前行
      const index = this.tableData.indexOf(row);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
      // 显示弹窗
      this.isModalVisible = true;
    },
    deleteItem(index) {
      // 删除操作
      // 这里可以根据索引 index 删除对应行的数据
      this.tableData.splice(index, 1);
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
</style>