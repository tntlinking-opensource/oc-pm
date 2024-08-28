import request from '@/utils/request'

export const getMicroVersionById = (id) =>
  request({
    url: '/micro/microVersion/' + id,
    method: 'get'
  })

export const listMicroVersionPage = (search) =>
  request({
    url: '/micro/microVersion/list',
    method: 'post',
    data: search
  })

export const listMicroVersionAll = (search) =>
  request({
    url: '/micro/microVersion/listAll',
    method: 'post',
    data: search
  })

export const saveMicroVersion = (microVersion) =>
  request({
    url: '/micro/microVersion/save',
    method: 'post',
    data: microVersion
  })

export const deleteMicroVersion = (microVersion) =>
  request({
    url: '/micro/microVersion/delete',
    method: 'post',
    data: microVersion
  })

export const bulkInsertMicroVersion = (microVersions) =>
  request({
    url: '/micro/microVersion/bulkInsert',
    method: 'post',
    data: microVersions
  })

export const bulkUpdateMicroVersion = (microVersions) =>
  request({
    url: '/micro/microVersion/bulkUpdate',
    method: 'post',
    data: microVersions
  })

export const bulkDeleteMicroVersion = (microVersions) =>
  request({
    url: '/micro/microVersion/bulkDelete',
    method: 'post',
    data: microVersions
  })
