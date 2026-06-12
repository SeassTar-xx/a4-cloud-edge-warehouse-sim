<template>
  <div>
    <!-- 等分布局 -->
    <!-- 中间间距 -->
    <!-- <el-row :gutter="10"> -->
      <!-- span="n"：分成n等分  -->
      <!-- <el-col :span="1">
        <div style="width: 100%; height:300px;background-color: skyblue;"></div>
      </el-col>
      <el-col :span="23">
        <div style="width: 100%; height:300px;background-color: red;"></div>
      </el-col>
    </el-row> -->
    <el-container>
      <!-- 侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
        <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">honey2024</span>
        </div>
        <!-- 菜单 -->
        <!-- collape水平折叠菜单 -->
        <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529" text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none" :default-active="$route.path">
          <!-- 菜单背景 -->
          <el-menu-item index="/element">
            <i class="el-icon-menu"></i>
            <span slot="title">元素页</span>
          </el-menu-item>
          <!-- 一级菜单 -->
          <el-menu-item index="/1">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/2">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <!-- 二级菜单 -->
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item>用户信息</el-menu-item>
            <el-menu-item>管理员信息</el-menu-item>
            <el-menu-item index="/">系统首页</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 内容区域 -->
      <el-container>
        <!--头部区域-->
        <el-header>
          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/element' }">元素页</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <!-- 头像 -->
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click.prevent="noop">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <!-- 主体内容 -->
        <el-main>
    <el-row>
      <el-col :span="6">
        <div style="padding:10px;text-align: center;border:1px solid black">
        <img style="width:100%" src="@/assets/logo.png" alt="">
        <div>这是个很牛逼的布局</div>
        <div style="color:red">价格：999</div>
      </div>
    </el-col>
      <el-col :span="6">
        <div style="padding:10px;text-align: center;border:1px solid black">
        <img style="width:100%" src="@/assets/logo.png" alt="">
        <div style="text-align: center;">这也是个很牛逼的布局</div>
        <div style="color:red">价格：999</div>
      </div>
      </el-col>
      <el-col :span="6">
        <div style="padding:10px;text-align: center;border:1px solid black">
        <img style="width:100%" src="@/assets/logo.png" alt="">
        <div style="text-align: center;">这还是个很牛逼的布局</div>
        <div style="color:red">价格：999</div>
      </div>
      </el-col>
      <el-col :span="6">
        <div style="padding:10px;text-align: center;border:1px solid black">
        <img style="width:100%" src="@/assets/logo.png" alt="">
        <div style="text-align: center;">这又是个很牛逼的布局</div>
        <div style="color:red">价格：999</div>
      </div>
      </el-col>
    </el-row>

    <!-- 按钮 -->
    <el-row>
      <el-col :span="24">
      <button>按钮</button>
      <el-button>Element独特按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
      <el-button type="warning">警告按钮</el-button>
      <el-button type="info">信息按钮</el-button>
      <el-button type="success">成功按钮</el-button>
      <!-- 朴素按钮+plain -->
      <el-button type="primary" plain>主要按钮</el-button>
      <!-- 加圆角+round -->
      <el-button type="primary" round>主要按钮</el-button>
      <el-button type="primary" round plain>主要按钮</el-button>
      <!-- 圆形按钮:一般会放图标 -->
      <el-button circle icon="el-icon-edit" type="primary"></el-button>
      <el-button circle icon="el-icon-delete" type="danger"></el-button>
      </el-col>
    </el-row>

    <!-- 表单：用v-model绑定值  -->
    <el-input style="width:200px" v-model="value" placeholder="输入内容"></el-input>
    <el-input type="textarea" style="width:200px" v-model="value" placeholder="输入内容"></el-input>
    <!-- 密码框 -->
    <el-input show-password style="width:200px" v-model="password" placeholder="请输入密码"></el-input>
    <!-- 前面搜索图标 -->
    <el-input style="width:200px" v-model="value1" placeholder="输入内容" prefix-icon="el-icon-search"></el-input>
    <!-- 后面用户图标,可清除 -->
    <el-input clearable style="width:200px" v-model="value2" placeholder="输入内容" suffix-icon="el-icon-user"></el-input>
    <el-button>搜索</el-button>

    <!-- 可搜索框,querySearch是方法 -->
    <el-row style="margin:20px 0">
      <el-autocomplete style="width:300px" placeholder="请输入内容，我来帮你猜" :fetch-suggestions="querySearch" :trigger-on-focus="false" v-model="value3"></el-autocomplete>

    <!-- 下拉框 -->
      <el-select v-model="select" @change="changeSelect">
        <!-- <el-option value="香蕉"></el-option>
        <el-option value="菠萝"></el-option>
        <el-option value="橘子"></el-option> -->
        <!-- v-for写法 -->
        <el-option v-for="item in fruits" :key="item.id" :label="item.name" :value="item.name"></el-option>
      </el-select>

      <!-- 选择name绑定card值并多选 -->
      <el-select v-model="select1" @change="changeSelectUser" multiple>
        <el-option v-for="item in users" :key="item.card" :label="item.name" :value="item.card"></el-option>
      </el-select>

      <!-- 可搜索下拉框 -->
      <el-select v-model="select2" @change="changeSelectUser" filterable>
        <el-option v-for="item in users" :key="item.card" :label="item.name" :value="item.card"></el-option>
      </el-select>
    </el-row>

    <el-row>
      <!-- 单选 -->
      <el-radio-group v-model="radio" @change="selsectRadio">
      <el-radio label="男"></el-radio>
      <el-radio label="女"></el-radio>
    </el-radio-group>
    <!-- 多选 -->
    <el-checkbox-group v-model="checkList" @change="selectChangeBox">
      <el-checkbox label="航航宝宝"></el-checkbox>
      <el-checkbox label="mua~"></el-checkbox>
      <el-checkbox label="小tt"></el-checkbox>
      <el-checkbox label="亲亲宝贝"></el-checkbox>
    </el-checkbox-group>
  </el-row>

    <el-row>
      <!-- 选择日期 -->
      <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="changeDate"></el-date-picker>
      <!-- 选择日期和时间 -->
      <el-date-picker v-model="datetime" type="datetime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm:ss" @change="changeDateTime"></el-date-picker>
      <!-- 选择日期范围 -->
      <el-date-picker v-model="daterange" type="daterange" start-placeholder="开始时间" end-placeholder="结束时间" @change="changeDateRange"></el-date-picker>
    </el-row>
    
    <!-- 表格 -->
    <el-row style="margin:20px 0">
      <el-table :data="tableData" height="250" border :header-cell-style="{background:'aliceblue',fontSize:'16px'}">
        <el-table-column label="序号" prop="id"></el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

    <!-- 表单 -->
    <el-form ref="form" :model="form" label-width="80px">
  <el-form-item label="活动名称">
    <el-input v-model="form.name"></el-input>
  </el-form-item>
  <el-form-item label="活动区域">
    <el-select v-model="form.region" placeholder="请选择活动区域">
      <el-option label="区域一" value="shanghai"></el-option>
      <el-option label="区域二" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="活动时间">
    <el-col :span="11">
      <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
    </el-col>
    <el-col class="line" :span="2">-</el-col>
    <el-col :span="11">
      <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
    </el-col>
  </el-form-item>
  <el-form-item label="即时配送">
    <el-switch v-model="form.delivery"></el-switch>
  </el-form-item>
  <el-form-item label="活动性质">
    <el-checkbox-group v-model="form.type">
      <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
      <el-checkbox label="地推活动" name="type"></el-checkbox>
      <el-checkbox label="线下主题活动" name="type"></el-checkbox>
      <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
    </el-checkbox-group>
  </el-form-item>
  <el-form-item label="特殊资源">
    <el-radio-group v-model="form.resource">
      <el-radio label="线上品牌商赞助"></el-radio>
      <el-radio label="线下场地免费"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="活动形式">
    <el-input type="textarea" v-model="form.desc"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">立即创建</el-button>
    <el-button>取消</el-button>
  </el-form-item>
</el-form>
  </el-main>
    </el-container>
    </el-container>
  </div>
</template>

<script>
      export default{
        name:'Element',
        data(){
          return{
            value:'',
            password:'',
            value1:'',
            value2:'',
            value3:'',
            select:'',
            select1:'',
            select2:'',
            coffees:[{value: '1.星巴克'},{value: '2.瑞幸咖啡'},{value: '3.库迪咖啡'}],
            fruits:[
              {name:'苹果',id:1},
              {name:'橘子',id:2},
              {name:'香蕉',id:3},
              {name:'航航宝贝',id:4},
              {name:'痰痰',id:5},
            ],
            users:[
              {name:'歆歆宝贝', card:'500101200402073533'},
              {name:'航航宝宝老公', card:'134554353533436'},
              {name:'小伊mua！', card:'54354635654354534'},
              {name:'最爱航航宝贝的男人!', card:'29833245345'},
            ],
            radio:'',
            checkList:[],
            date:'',
            datetime:'',
            daterange:'',
            tableData:[
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
              {name:'徐歆',address:'广东省深圳市',id:1,age:'30'},
            ],
            isCollapse: false,  // 不收缩
            asideWidth: '200px',
            collapseIcon: 'el-icon-s-fold',
            form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
          }
        }
      },
      methods: {
        //搜索建议
        querySearch(query,cd){//回调函数
          let result=query?this.coffees.filter(v => v.value.includes(query)):this.coffees
          console.log(result)
          cd(result)
        },
        //函数获取下拉框选中的值
        changeSelect(){
          console.log(this.select)
        },
        //下拉多选
        changeSelectUser(){
          console.log(this.select1)
        },
        //单选
        selsectRadio(){
          alert(this.radio)
        },
        //多选
        selectChangeBox(){
          console.log(this.checklist)
        },
        //选日期
        changeDate(){
          console.log(this.date)
        },
        changeDateTime(){
          console.log(this.datetime)
        },
        changeDateRange(){
          console.log(this.daterange)
        },
        edit(row){
          alert(row.name)
          //this.$message.success(row.mane)
        },
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
    //表单
    onSubmit() {
        console.log('submit!');
      }
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