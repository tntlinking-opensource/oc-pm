import request from '@/utils/request'

export const getMasterDataRecordsById = (id) =>
  request({
    url: '/databus/masterDataRecords/' + id,
    method: 'get'
  })

export const listMasterDataRecordsPage = (search) =>
  request({
    url: '/databus/masterDataRecords/list',
    method: 'post',
    data: search
  })

export const listMasterDataRecordsAll = (search) =>
  request({
    url: '/databus/masterDataRecords/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataRecords = (masterDataRecords) =>
  request({
    url: '/databus/masterDataRecords/save',
    method: 'post',
    data: masterDataRecords
  })

export const deleteMasterDataRecords = (masterDataRecords) =>
  request({
    url: '/databus/masterDataRecords/delete',
    method: 'post',
    data: masterDataRecords
  })

export const bulkInsertMasterDataRecords = (masterDataRecordss) =>
  request({
    url: '/databus/masterDataRecords/bulkInsert',
    method: 'post',
    data: masterDataRecordss
  })

export const bulkUpdateMasterDataRecords = (masterDataRecordss) =>
  request({
    url: '/databus/masterDataRecords/bulkUpdate',
    method: 'post',
    data: masterDataRecordss
  })

export const bulkDeleteMasterDataRecords = (masterDataRecordss) =>
  request({
    url: '/databus/masterDataRecords/bulkDelete',
    method: 'post',
    data: masterDataRecordss
  })
