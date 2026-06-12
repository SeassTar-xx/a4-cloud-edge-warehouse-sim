import request from '@/utils/request'

// 查询采购入库表列表
export function listArriveorder(query) {
  return request({
    url: '/arriveorder/arriveorder/list',
    method: 'get',
    params: query
  })
}

// 查询采购入库表详细
export function getArriveorder(id) {
  return request({
    url: '/arriveorder/arriveorder/' + id,
    method: 'get'
  })
}

// 新增采购入库表
export function addArriveorder(data) {
  return request({
    url: '/arriveorder/arriveorder',
    method: 'post',
    data: data
  })
}

// 修改采购入库表
export function updateArriveorder(data) {
  return request({
    url: '/arriveorder/arriveorder',
    method: 'put',
    data: data
  })
}

// 删除采购入库表
export function delArriveorder(id) {
  return request({
    url: '/arriveorder/arriveorder/' + id,
    method: 'delete'
  })
}
