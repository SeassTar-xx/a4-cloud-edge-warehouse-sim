import request from '@/utils/request'

export function listMrp(query) {
  return request({
    url: '/algorithm/mrp/list',
    method: 'get',
    params: query
  })
}

export function getMrp(id) {
  return request({
    url: '/algorithm/mrp/' + id,
    method: 'get'
  })
}

export function addMrp(data) {
  return request({
    url: '/algorithm/mrp',
    method: 'post',
    data: data
  })
}

export function updateMrp(data) {
  return request({
    url: '/algorithm/mrp',
    method: 'put',
    data: data
  })
}

export function delMrp(id) {
  return request({
    url: '/algorithm/mrp/' + id,
    method: 'delete'
  })
}
