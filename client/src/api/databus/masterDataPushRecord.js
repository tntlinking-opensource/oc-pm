import request from '@/utils/request'

export const getMasterDataPushRecordById = (id) =>
  request({
    url: '/databus/masterDataPushRecord/' + id,
    method: 'get'
  })

export const listMasterDataPushRecordPage = (search) =>
  request({
    url: '/databus/masterDataPushRecord/list',
    method: 'post',
    data: search
  })

export const listMasterDataPushRecordAll = (search) =>
  request({
    url: '/databus/masterDataPushRecord/listAll',
    method: 'post',
    data: search
  })

export const listTargetAppAll = (search) =>
  request({
    url: '/databus/masterDataPushRecord/targetAppListAll',
    method: 'post',
    data: search
  })

export const saveMasterDataPushRecord = (masterDataPushRecord) =>
  request({
    url: '/databus/masterDataPushRecord/save',
    method: 'post',
    data: masterDataPushRecord
  })

export const deleteMasterDataPushRecord = (masterDataPushRecord) =>
  request({
    url: '/databus/masterDataPushRecord/delete',
    method: 'post',
    data: masterDataPushRecord
  })

export const bulkInsertMasterDataPushRecord = (masterDataPushRecords) =>
  request({
    url: '/databus/masterDataPushRecord/bulkInsert',
    method: 'post',
    data: masterDataPushRecords
  })

export const bulkUpdateMasterDataPushRecord = (masterDataPushRecords) =>
  request({
    url: '/databus/masterDataPushRecord/bulkUpdate',
    method: 'post',
    data: masterDataPushRecords
  })

export const bulkDeleteMasterDataPushRecord = (masterDataPushRecords) =>
  request({
    url: '/databus/masterDataPushRecord/bulkDelete',
    method: 'post',
    data: masterDataPushRecords
  })
