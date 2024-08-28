import request from '@/utils/request'

export const getMasterDataDetailById = (id) =>
  request({
    url: '/databus/masterDataDetail/' + id,
    method: 'get'
  })

export const listMasterDataDetailPage = (search) =>
  request({
    url: '/databus/masterDataDetail/list',
    method: 'post',
    data: search
  })

export const listMasterDataDetailAll = (search) =>
  request({
    url: '/databus/masterDataDetail/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataDetail = (masterDataDetail) =>
  request({
    url: '/databus/masterDataDetail/save',
    method: 'post',
    data: masterDataDetail
  })

export const deleteMasterDataDetail = (masterDataDetail) =>
  request({
    url: '/databus/masterDataDetail/delete',
    method: 'post',
    data: masterDataDetail
  })

export const bulkInsertMasterDataDetail = (masterDataDetails) =>
  request({
    url: '/databus/masterDataDetail/bulkInsert',
    method: 'post',
    data: masterDataDetails
  })

export const bulkUpdateMasterDataDetail = (masterDataDetails) =>
  request({
    url: '/databus/masterDataDetail/bulkUpdate',
    method: 'post',
    data: masterDataDetails
  })

export const bulkDeleteMasterDataDetail = (masterDataDetails) =>
  request({
    url: '/databus/masterDataDetail/bulkDelete',
    method: 'post',
    data: masterDataDetails
  })
