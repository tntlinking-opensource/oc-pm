import request from '@/utils/request'

export const getScheduleManageById = (id) =>
  request({
    url: '/projectManage/scheduleManage/' + id,
    method: 'get'
  })

export const getscheduleByid = (id) =>
  request({
    url: '/projectManage/scheduleManage/getscheduleByid/' + id,
    method: 'get'
  })

export const listScheduleManagePage = (search) =>
  request({
    url: '/projectManage/scheduleManage/list',
    method: 'post',
    data: search
  })

export const listScheduleManageAll = (search) =>
  request({
    url: '/projectManage/scheduleManage/listAll',
    method: 'post',
    data: search
  })

export const saveScheduleManage = (scheduleManage) =>
  request({
    url: '/projectManage/scheduleManage/save',
    method: 'post',
    data: scheduleManage
  })

export const modifyScheduleStatus = (scheduleManage) =>
  request({
    url: '/projectManage/scheduleManage/modifyScheduleStatus',
    method: 'post',
    data: scheduleManage
  })

export const deleteScheduleManage = (scheduleManage) =>
  request({
    url: '/projectManage/scheduleManage/delete',
    method: 'post',
    data: scheduleManage
  })

export const bulkInsertScheduleManage = (scheduleManages) =>
  request({
    url: '/projectManage/scheduleManage/bulkInsert',
    method: 'post',
    data: scheduleManages
  })

export const bulkUpdateScheduleManage = (scheduleManages) =>
  request({
    url: '/projectManage/scheduleManage/bulkUpdate',
    method: 'post',
    data: scheduleManages
  })

export const bulkDeleteScheduleManage = (scheduleManages) =>
  request({
    url: '/projectManage/scheduleManage/bulkDelete',
    method: 'post',
    data: scheduleManages
  })
