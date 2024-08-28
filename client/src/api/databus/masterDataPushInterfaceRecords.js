import request from '@/utils/request'

export const getMasterDataPushInterfaceRecordsById = (id) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/' + id,
    method: 'get'
  })

export const listMasterDataPushInterfaceRecordsPage = (search) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/list',
    method: 'post',
    data: search
  })

export const listMasterDataPushInterfaceRecordsAll = (search) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/listAll',
    method: 'post',
    data: search
  })

export const saveMasterDataPushInterfaceRecords = (masterDataPushInterfaceRecords) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/save',
    method: 'post',
    data: masterDataPushInterfaceRecords
  })

export const deleteMasterDataPushInterfaceRecords = (masterDataPushInterfaceRecords) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/delete',
    method: 'post',
    data: masterDataPushInterfaceRecords
  })

export const bulkInsertMasterDataPushInterfaceRecords = (masterDataPushInterfaceRecordss) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/bulkInsert',
    method: 'post',
    data: masterDataPushInterfaceRecordss
  })

export const bulkUpdateMasterDataPushInterfaceRecords = (masterDataPushInterfaceRecordss) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/bulkUpdate',
    method: 'post',
    data: masterDataPushInterfaceRecordss
  })

export const bulkDeleteMasterDataPushInterfaceRecords = (masterDataPushInterfaceRecordss) =>
  request({
    url: '/databus/masterDataPushInterfaceRecords/bulkDelete',
    method: 'post',
    data: masterDataPushInterfaceRecordss
  })
