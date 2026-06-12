import request from '@/utils/request'

// 查询returnorder列表
export function listReturnorder(query) {
  return request({
    url: '/returnorder/returnorder/list',
    method: 'get',
    params: query
  })
}

// 查询returnorder详细
export function getReturnorder(id) {
  return request({
    url: '/returnorder/returnorder/' + id,
    method: 'get'
  })
}

// 新增returnorder
export function addReturnorder(data) {
  return request({
    url: '/returnorder/returnorder',
    method: 'post',
    data: data
  })
}

// 修改returnorder
export function updateReturnorder(data) {
  return request({
    url: '/returnorder/returnorder',
    method: 'put',
    data: data
  })
}

// 删除returnorder
export function delReturnorder(id) {
  return request({
    url: '/returnorder/returnorder/' + id,
    method: 'delete'
  })
}
