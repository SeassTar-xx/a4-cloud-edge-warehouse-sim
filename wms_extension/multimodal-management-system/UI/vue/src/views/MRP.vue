<template>
  <div>
    <!-- 外部盒子嵌套 -->
    <el-container>
      <!-- 侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
        <!-- 系统名称 -->
        <!-- <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">honey2024</span>
        </div> -->
        <!-- 菜单 -->
        <!-- collape水平折叠菜单 -->
        <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529" text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none" :default-active="$route.path">
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
          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/homePage' }">生产</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/1_3' }">物料需求计划</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>个人信息</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>编辑个人信息</el-dropdown-item>
                <el-dropdown-item @click.prevent="noop">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <!--主体区域-->
        <el-main>
          
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'MRP',
  data(){
    return{
      isCollapse: false, // 不收缩
      asideWidth: "200px",
      collapseIcon: "el-icon-s-fold",
    }
  },
  methods: {
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    },
    noop() {
    // 什么也不做
  },
  }
}
</script>

<style>
.el-menu--inline {
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
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
.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
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
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;   /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}
</style>