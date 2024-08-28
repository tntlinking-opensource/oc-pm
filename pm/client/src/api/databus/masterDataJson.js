import request from '@/utils/request'

export const getMasterDataJsonById = (id) =>
  request({
    url: '/databus/masterDataJson/' + id,
    method: 'get'
  })

export const listMasterDataJsonPage = (search) =>
  request({
    url: '/databus/masterDataJson/list',
    method: 'post',
    data: search
  })

export const listMasterDataJsonAll = (search) =>
  request({
    url: '/databus/masterDataJson/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataJson = (masterDataJson) =>
  request({
    url: '/databus/masterDataJson/save',
    method: 'post',
    data: masterDataJson
  })

export const deleteMasterDataJson = (masterDataJson) =>
  request({
    url: '/databus/masterDataJson/delete',
    method: 'post',
    data: masterDataJson
  })

export const bulkInsertMasterDataJson = (masterDataJsons) =>
  request({
    url: '/databus/masterDataJson/bulkInsert',
    method: 'post',
    data: masterDataJsons
  })

export const bulkUpdateMasterDataJson = (masterDataJsons) =>
  request({
    url: '/databus/masterDataJson/bulkUpdate',
    method: 'post',
    data: masterDataJsons
  })

export const bulkDeleteMasterDataJson = (masterDataJsons) =>
  request({
    url: '/databus/masterDataJson/bulkDelete',
    method: 'post',
    data: masterDataJsons
  })
