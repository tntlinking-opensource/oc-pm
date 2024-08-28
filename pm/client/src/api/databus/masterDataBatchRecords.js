import request from '@/utils/request'

export const getMasterDataBatchRecordsById = (id) =>
  request({
    url: '/databus/masterDataBatchRecords/' + id,
    method: 'get'
  })

export const listMasterDataBatchRecordsPage = (search) =>
  request({
    url: '/databus/masterDataBatchRecords/list',
    method: 'post',
    data: search
  })

export const listMasterDataBatchRecordsAll = (search) =>
  request({
    url: '/databus/masterDataBatchRecords/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataBatchRecords = (masterDataBatchRecords) =>
  request({
    url: '/databus/masterDataBatchRecords/save',
    method: 'post',
    data: masterDataBatchRecords
  })

export const deleteMasterDataBatchRecords = (masterDataBatchRecords) =>
  request({
    url: '/databus/masterDataBatchRecords/delete',
    method: 'post',
    data: masterDataBatchRecords
  })

export const bulkInsertMasterDataBatchRecords = (masterDataBatchRecordss) =>
  request({
    url: '/databus/masterDataBatchRecords/bulkInsert',
    method: 'post',
    data: masterDataBatchRecordss
  })

export const bulkUpdateMasterDataBatchRecords = (masterDataBatchRecordss) =>
  request({
    url: '/databus/masterDataBatchRecords/bulkUpdate',
    method: 'post',
    data: masterDataBatchRecordss
  })

export const bulkDeleteMasterDataBatchRecords = (masterDataBatchRecordss) =>
  request({
    url: '/databus/masterDataBatchRecords/bulkDelete',
    method: 'post',
    data: masterDataBatchRecordss
  })
