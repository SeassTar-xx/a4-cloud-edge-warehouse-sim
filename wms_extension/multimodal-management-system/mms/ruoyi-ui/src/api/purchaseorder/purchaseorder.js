import request from '@/utils/request'

// 查询采购订单列表
export function listPurchaseorder(query) {
  return request({
    url: '/purchaseorder/purchaseorder/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单详细
export function getPurchaseorder(id) {
  return request({
    url: '/purchaseorder/purchaseorder/' + id,
    method: 'get'
  })
}

// 新增采购订单
export function addPurchaseorder(data) {
  return request({
    url: '/purchaseorder/purchaseorder',
    method: 'post',
    data: data
  })
}

// 修改采购订单
export function updatePurchaseorder(data) {
  return request({
    url: '/purchaseorder/purchaseorder',
    method: 'put',
    data: data
  })
}

// 删除采购订单
export function delPurchaseorder(id) {
  return request({
    url: '/purchaseorder/purchaseorder/' + id,
    method: 'delete'
  })
}
