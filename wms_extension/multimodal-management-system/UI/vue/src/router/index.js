import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component:()=>import('@/views/HomeView.vue')
  },
  {
    path: '/element',
    name: 'Element',
    component:()=>import('@/views/Element.vue')
  },
  {
    path: '/homePage',
    name: 'homePage',
    component:()=>import('@/views/homePage.vue')
  },
  {
    path: '/6_1',
    name: 'changeInform',
    component:()=>import('@/views/changeInform.vue')
  },
  {
    path: '/1_1',
    name: 'MPS',
    component:()=>import('@/views/MPS.vue')
  },
  {
    path: '/1_2',
    name: 'MPSlist',
    component:()=>import('@/views/MPSlist.vue')
  },
  {
    path: '/1_3',
    name: 'MRP',
    component:()=>import('@/views/MRP.vue')
  },
  {
    path: '/5_1',
    name: 'unit',
    component:()=>import('@/views/unit.vue')
  },
  {
    path: '/5_2',
    name: 'commodity',
    component:()=>import('@/views/commodity.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
