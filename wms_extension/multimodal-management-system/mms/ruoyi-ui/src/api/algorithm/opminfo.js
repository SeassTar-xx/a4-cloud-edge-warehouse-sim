import request from '@/utils/request'

export function listOpminfo(query) {
  return request({
    url: '/algorithm/opminfo/list',
    method: 'get',
    params: query
  })
}

export function getOpminfo(itemCode) {
  return request({
    url: '/algorithm/opminfo/' + itemCode,
    method: 'get'
  })
}

export function addOpminfo(data) {
  return request({
    url: '/algorithm/opminfo',
    method: 'post',
    data: data
  })
}

export function updateOpminfo(data) {
  return request({
    url: '/algorithm/opminfo',
    method: 'put',
    data: data
  })
}

export function delOpminfo(itemCode) {
  return request({
    url: '/algorithm/opminfo/' + itemCode,
    method: 'delete'
  })
}
