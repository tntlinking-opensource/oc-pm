import request from '@/utils/request'

export const getDocumentManageById = (id) =>
  request({
    url: '/documentManage/documentManage/' + id,
    method: 'get'
  })

export const listDocumentManagePage = (search) =>
  request({
    url: '/documentManage/documentManage/list',
    method: 'post',
    data: search
  })

export const listDocumentManageAll = (search) =>
  request({
    url: '/documentManage/documentManage/listAll',
    method: 'post',
    data: search
  })

export const saveDocumentManage = (formData) =>
  request({
    url: '/documentManage/documentManage/save',
    method: 'post',
    data: formData
  })

export const deleteDocumentManage = (documentManage) =>
  request({
    url: '/documentManage/documentManage/delete',
    method: 'post',
    data: documentManage
  })

export const bulkInsertDocumentManage = (documentManages) =>
  request({
    url: '/documentManage/documentManage/bulkInsert',
    method: 'post',
    data: documentManages
  })

export const bulkUpdateDocumentManage = (documentManages) =>
  request({
    url: '/documentManage/documentManage/bulkUpdate',
    method: 'post',
    data: documentManages
  })

export const bulkDeleteDocumentManage = (documentManages) =>
  request({
    url: '/documentManage/documentManage/bulkDelete',
    method: 'post',
    data: documentManages
  })
