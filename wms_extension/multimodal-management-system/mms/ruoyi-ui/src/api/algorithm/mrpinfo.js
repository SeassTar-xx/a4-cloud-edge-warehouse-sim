import request from '@/utils/request'

export function listMrpinfo(query) {
  return request({
    url: '/algorithm/mrpinfo/list',
    method: 'get',
    params: query
  })
}

export function getMrpinfo(itemCode) {
  return request({
    url: '/algorithm/mrpinfo/' + itemCode,
    method: 'get'
  })
}

export function addMrpinfo(data) {
  return request({
    url: '/algorithm/mrpinfo',
    method: 'post',
    data: data
  })
}

export function updateMrpinfo(data) {
  return request({
    url: '/algorithm/mrpinfo',
    method: 'put',
    data: data
  })
}

export function delMrpinfo(itemCode) {
  return request({
    url: '/algorithm/mrpinfo/' + itemCode,
    method: 'delete'
  })
}
