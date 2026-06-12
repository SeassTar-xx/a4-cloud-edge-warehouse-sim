import request from '@/utils/request'

// 查询单位列表
export function listUnit(query) {
  return request({
    url: '/unit/unit/list',
    method: 'get',
    params: query
  })
}

// 查询单位详细
export function getUnit(uid) {
  return request({
    url: '/unit/unit/' + uid,
    method: 'get'
  })
}

// 新增单位
export function addUnit(data) {
  return request({
    url: '/unit/unit',
    method: 'post',
    data: data
  })
}

// 修改单位
export function updateUnit(data) {
  return request({
    url: '/unit/unit',
    method: 'put',
    data: data
  })
}

// 删除单位
export function delUnit(uid) {
  return request({
    url: '/unit/unit/' + uid,
    method: 'delete'
  })
}
