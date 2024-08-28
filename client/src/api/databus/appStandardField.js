import request from '@/utils/request'

export const getAppStandardFieldById = (id) =>
  request({
    url: '/databus/appStandardField/' + id,
    method: 'get'
  })

export const listAppStandardFieldPage = (search) =>
  request({
    url: '/databus/appStandardField/list',
    method: 'post',
    data: search
  })

export const listAppStandardFieldAll = (search) =>
  request({
    url: '/databus/appStandardField/listAll',
    method: 'post',
    data: search
  })

export const saveAppStandardField = (appStandardField) =>
  request({
    url: '/databus/appStandardField/save',
    method: 'post',
    data: appStandardField
  })

export const deleteAppStandardField = (appStandardField) =>
  request({
    url: '/databus/appStandardField/delete',
    method: 'post',
    data: appStandardField
  })

export const bulkInsertAppStandardField = (appStandardFields) =>
  request({
    url: '/databus/appStandardField/bulkInsert',
    method: 'post',
    data: appStandardFields
  })

export const bulkUpdateAppStandardField = (appStandardFields) =>
  request({
    url: '/databus/appStandardField/bulkUpdate',
    method: 'post',
    data: appStandardFields
  })

export const bulkDeleteAppStandardField = (appStandardFields) =>
  request({
    url: '/databus/appStandardField/bulkDelete',
    method: 'post',
    data: appStandardFields
  })
