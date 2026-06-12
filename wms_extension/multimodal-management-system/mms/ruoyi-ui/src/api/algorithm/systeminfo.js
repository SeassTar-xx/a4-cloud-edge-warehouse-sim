import request from '@/utils/request'

export function listSysteminfo() {
  return request({
    url: '/algorithm/systeminfo/list',
    method: 'get'
  })
}

export function getSysteminfo() {
  return request({
    url: '/algorithm/systeminfo',
    method: 'get'
  })
}

export function saveSysteminfo(data) {
  return request({
    url: '/algorithm/systeminfo',
    method: 'put',
    data: data
  })
}
