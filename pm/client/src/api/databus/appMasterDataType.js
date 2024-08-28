import request from '@/utils/request'

export const getAppMasterDataTypeById = (id) =>
  request({
    url: '/databus/appMasterDataType/' + id,
    method: 'get'
  })

export const listAppMasterDataTypePage = (search) =>
  request({
    url: '/databus/appMasterDataType/list',
    method: 'post',
    data: search
  })

export const listAppMasterDataTypeAll = (search) =>
  request({
    url: '/databus/appMasterDataType/listAll',
    method: 'post',
    data: search
  })

export const saveAppMasterDataType = (appMasterDataType) =>
  request({
    url: '/databus/appMasterDataType/save',
    method: 'post',
    data: appMasterDataType
  })

export const deleteAppMasterDataType = (appMasterDataType) =>
  request({
      url: '/databus/appMasterDataType/delete',
    method: 'post',
    data: appMasterDataType
  })

export const bulkInsertAppMasterDataType = (appMasterDataTypes) =>
  request({
    url: '/databus/appMasterDataType/bulkInsert',
    method: 'post',
    data: appMasterDataTypes
  })

export const bulkUpdateAppMasterDataType = (appMasterDataTypes) =>
  request({
    url: '/databus/appMasterDataType/bulkUpdate',
    method: 'post',
    data: appMasterDataTypes
  })

export const bulkDeleteAppMasterDataType = (appMasterDataTypes) =>
  request({
    url: '/databus/appMasterDataType/bulkDelete',
    method: 'post',
    data: appMasterDataTypes
  })
