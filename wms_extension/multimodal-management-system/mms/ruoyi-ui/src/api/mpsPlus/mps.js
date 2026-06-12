import request from '@/utils/request'

// 查询mps列表
export function listMps(query) {
  return request({
    url: '/mpsPlus/mps/list',
    method: 'get',
    params: query
  })
}

// 查询mps详细
export function getMps(id) {
  return request({
    url: '/mpsPlus/mps/' + id,
    method: 'get'
  })
}

// 新增mps
export function addMps(data) {
  return request({
    url: '/mpsPlus/mps',
    method: 'post',
    data: data
  })
}

// 修改mps
export function updateMps(data) {
  return request({
    url: '/mpsPlus/mps',
    method: 'put',
    data: data
  })
}

// 删除mps
export function delMps(id) {
  return request({
    url: '/mpsPlus/mps/' + id,
    method: 'delete'
  })
}

// 运行MPS算法
export function runMps(id) {
  return request({
    url: '/mpsPlus/mps/run/' + id,
    method: 'post'
  })
}
