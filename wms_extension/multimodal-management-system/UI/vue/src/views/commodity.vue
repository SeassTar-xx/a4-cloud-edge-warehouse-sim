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
            <el-breadcrumb-item :to="{ path: '/5_2' }">商品</el-breadcrumb-item>
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
          <el-button type="primary" plain @click="showAddDialog"
            >新增</el-button
          >
          <hr />
          <!-- 商品表格 -->
          <el-table
            :data="products"
            style="width: 100%"
            border
            :header-cell-style="{ background: 'aliceblue', fontSize: '16px' }"
          >
            <el-table-column label="商品编码" prop="code"></el-table-column>
            <el-table-column label="商品名称" prop="name"></el-table-column>
            <el-table-column
              label="预期销量"
              prop="expectedSales"
            ></el-table-column>
            <el-table-column label="最大库存" prop="maxStock"></el-table-column>
            <el-table-column
              label="安全库存"
              prop="safetyStock"
            ></el-table-column>
            <el-table-column label="单位" prop="unit"></el-table-column>
            <el-table-column label="提前期" prop="leadTime"></el-table-column>
            <el-table-column
              label="提前期单位"
              prop="leadTimeUnit"
            ></el-table-column>
          </el-table>
          <!-- 表单 -->
          <el-dialog :visible.sync="dialogVisible" title="新增商品">
            <p style="font-weight: bold; margin-bottom: 10px">
              商品编码: {{ generateProductCode() }}
            </p>
            <el-form ref="addForm" :model="formData" label-width="100px">
              <el-form-item label="商品名称" prop="name">
                <el-input v-model="formData.name"></el-input>
              </el-form-item>
              <el-form-item label="单位">
                <el-select
                  v-model="formData.unit"
                  filterable
                  placeholder="请选择单位"
                >
                  <el-option
                    v-for="unit in units"
                    :key="unit.id"
                    :label="unit.name"
                    :value="unit.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="CGC码">
                <el-radio-group v-model="formData.cgc">
                  <el-radio label="A">A</el-radio>
                  <el-radio label="B">B</el-radio>
                  <el-radio label="C1">C1</el-radio>
                  <el-radio label="C2">C2</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="提前期" prop="leadTime">
          <el-input v-model="formData.leadTime"></el-input>
        </el-form-item>
        <el-form-item label="提前期单位" prop="leadTimeUnit">
          <el-select v-model="formData.leadTimeUnit" placeholder="请选择提前期单位">
            <el-option label="天" value="天"></el-option>
            <el-option label="周" value="周"></el-option>
          </el-select>
        </el-form-item>
              <el-form-item label="是否考虑安全库存">
                <el-checkbox v-model="formData.safeStock">是</el-checkbox>
              </el-form-item>
              <el-form-item v-if="formData.safeStock" label="安全库存">
                <el-input v-model="formData.safeStockAmount"></el-input>
              </el-form-item>
              <el-form-item label="预期销量">
                <el-input v-model="formData.expectedSales"></el-input>
              </el-form-item>
              <el-form-item label="预期销量单位">
                <el-select v-model="formData.salesUnit">
                  <el-option label="天" value="day"></el-option>
                  <el-option label="周" value="week"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="最大库存量">
                <el-input v-model="formData.maxStock"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSubmit">确定</el-button>
            </div>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "commodity",
  data() {
    return {
      isCollapse: false, // 不收缩
      asideWidth: "200px",
      collapseIcon: "el-icon-s-fold",
      dialogVisible: false,
      formData: {
        name: "",
        unit: "",
        cgc: "",
        safeStock: false,
        safeStockAmount: "",
        expectedSales: "",
        salesUnit: "",
        maxStock: "",
      },
      units: [
        // 假设单位是从数据库中获取的
        { id: "1", name: "单位1" },
        { id: "2", name: "单位2" },
        { id: "3", name: "单位3" },
      ],
      productCount: 0,
      products: [],
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
    showAddDialog() {
      this.dialogVisible = true;
    },
    generateProductCode() {
      // 生成商品编码格式为“SP”+“添加顺序”
      return `SP${(this.productCount + 1).toString().padStart(3, "0")}`;
    },
    handleSubmit() {
      const newProduct = { ...this.formData, code: this.generateProductCode() };
      // 提交表单数据到数据库
      console.log('表单数据已提交：', newProduct);
      this.$message.success("保存成功");
      // 假设发起一个API调用来将数据提交到服务器
      // axios.post('/api/addProduct', this.newProduct)
      //   .then(response => {
      //     console.log('商品添加成功');
      //   })
      //   .catch(error => {
      //     console.error('添加商品时出错：', error);
      //   });

      // 增加商品编码后，更新商品计数
      this.productCount++;
      // 将商品信息加入到列表中
      this.products.push(newProduct);
      //关闭对话框
      this.dialogVisible = false;
      // 清空表单数据
      this.formData = {
        name: "",
        expectedSales: "",
        maxStock: "",
        safetyStock: "",
        unit: "",
        leadTime: "",
        leadTimeUnit: "",
      };
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