import request from '@/utils/request'

// 查询初级库存录入列表
export function listStock(query) {
  return request({
    url: '/stock/stock/list',
    method: 'get',
    params: query
  })
}

// 查询初级库存录入详细
export function getStock(id) {
  return request({
    url: '/stock/stock/' + id,
    method: 'get'
  })
}

// 新增初级库存录入
export function addStock(data) {
  return request({
    url: '/stock/stock',
    method: 'post',
    data: data
  })
}

// 修改初级库存录入
export function updateStock(data) {
  return request({
    url: '/stock/stock',
    method: 'put',
    data: data
  })
}

// 删除初级库存录入
export function delStock(id) {
  return request({
    url: '/stock/stock/' + id,
    method: 'delete'
  })
}
