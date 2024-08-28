import request from '@/utils/request'

export const getMasterDataPushRecordsById = (id) =>
  request({
    url: '/databus/masterDataPushRecords/' + id,
    method: 'get'
  })

export const listMasterDataPushRecordsPage = (search) =>
  request({
    url: '/databus/masterDataPushRecords/list',
    method: 'post',
    data: search
  })

export const listMasterDataPushRecordsAll = (search) =>
  request({
    url: '/databus/masterDataPushRecords/listAll',
    method: 'post',
    data: search
  })

export const listTargetAppAll = (search) =>
  request({
    url: '/databus/masterDataPushRecords/targetAppListAll',
    method: 'post',
    data: search
  })

export const saveMasterDataPushRecords = (masterDataPushRecords) =>
  request({
    url: '/databus/masterDataPushRecords/save',
    method: 'post',
    data: masterDataPushRecords
  })

export const deleteMasterDataPushRecords = (masterDataPushRecords) =>
  request({
    url: '/databus/masterDataPushRecords/delete',
    method: 'post',
    data: masterDataPushRecords
  })

export const bulkInsertMasterDataPushRecords = (masterDataPushRecordss) =>
  request({
    url: '/databus/masterDataPushRecords/bulkInsert',
    method: 'post',
    data: masterDataPushRecordss
  })

export const bulkUpdateMasterDataPushRecords = (masterDataPushRecordss) =>
  request({
    url: '/databus/masterDataPushRecords/bulkUpdate',
    method: 'post',
    data: masterDataPushRecordss
  })

export const bulkDeleteMasterDataPushRecords = (masterDataPushRecordss) =>
  request({
    url: '/databus/masterDataPushRecords/bulkDelete',
    method: 'post',
    data: masterDataPushRecordss
  })
