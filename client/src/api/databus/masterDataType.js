import request from '@/utils/request'

export const getMasterDataTypeById = (id) =>
  request({
    url: '/databus/masterDataType/' + id,
    method: 'get'
  })

export const listMasterDataTypePage = (search) =>
  request({
      url: '/databus/masterDataType/list',
    method: 'post',
    data: search
  })

export const listMasterDataTypeAll = (search) =>
  request({
    url: '/databus/masterDataType/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataType = (masterDataType) =>
  request({
    url: '/databus/masterDataType/save',
    method: 'post',
    data: masterDataType
  })

export const deleteMasterDataType = (masterDataType) =>
  request({
    url: '/databus/masterDataType/delete',
    method: 'post',
    data: masterDataType
  })

export const bulkInsertMasterDataType = (masterDataTypes) =>
  request({
    url: '/databus/masterDataType/bulkInsert',
    method: 'post',
    data: masterDataTypes
  })

export const bulkUpdateMasterDataType = (masterDataTypes) =>
  request({
    url: '/databus/masterDataType/bulkUpdate',
    method: 'post',
    data: masterDataTypes
  })

export const bulkDeleteMasterDataType = (masterDataTypes) =>
  request({
    url: '/databus/masterDataType/bulkDelete',
    method: 'post',
    data: masterDataTypes
  })
export const listMicroVersionAll = (search) =>
  request({
    url: '/databus/masterDataType/listMicroVersionAll',
    method: 'post',
    data: search
  })
