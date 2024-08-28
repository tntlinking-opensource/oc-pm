import request from '@/utils/request'

export const getReportManageById = (id) =>
  request({
    url: '/projectManage/reportManage/' + id,
    method: 'get'
  })

export const listReportManagePage = (search) =>
  request({
    url: '/projectManage/reportManage/list',
    method: 'post',
    data: search
  })

export const listReportManageAll = (search) =>
  request({
    url: '/projectManage/reportManage/listAll',
    method: 'post',
    data: search
  })

export const saveReportManage = (formData) =>
  request({
    url: '/projectManage/reportManage/save',
    method: 'post',
    data: formData
  })

export const deleteReportManage = (reportManage) =>
  request({
    url: '/projectManage/reportManage/delete',
    method: 'post',
    data: reportManage
  })

export const bulkInsertReportManage = (reportManages) =>
  request({
    url: '/projectManage/reportManage/bulkInsert',
    method: 'post',
    data: reportManages
  })

export const bulkUpdateReportManage = (reportManages) =>
  request({
    url: '/projectManage/reportManage/bulkUpdate',
    method: 'post',
    data: reportManages
  })

export const bulkDeleteReportManage = (reportManages) =>
  request({
    url: '/projectManage/reportManage/bulkDelete',
    method: 'post',
    data: reportManages
  })
