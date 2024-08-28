import request from '@/utils/request'

export const getMicroAppById = (id) =>
  request({
    url: '/micro/microApp/' + id,
    method: 'get'
  })

export const listMicroAppPage = (search) =>
  request({
    url: '/micro/microApp/list',
    method: 'post',
    data: search
  })

export const listMicroAppAll = (search) =>
  request({
    url: '/micro/microApp/listAll',
    method: 'post',
    data: search
  })

export const saveMicroApp = (formData) =>
  request({
    url: '/micro/microApp/save',
    method: 'post',
    data: formData
  })

export const deleteMicroApp = (microApp) =>
  request({
    url: '/micro/microApp/delete',
    method: 'post',
    data: microApp
  })

export const bulkInsertMicroApp = (microApps) =>
  request({
    url: '/micro/microApp/bulkInsert',
    method: 'post',
    data: microApps
  })

export const bulkUpdateMicroApp = (microApps) =>
  request({
    url: '/micro/microApp/bulkUpdate',
    method: 'post',
    data: microApps
  })

export const bulkDeleteMicroApp = (microApps) =>
  request({
    url: '/micro/microApp/bulkDelete',
    method: 'post',
    data: microApps
  })
