import request from '@/utils/request'

export const getMilestoneManageById = (id) =>
  request({
    url: '/projectManage/milestoneManage/' + id,
    method: 'get'
  })

export const getBymilestone = (id) =>
  request({
    url: '/projectManage/milestoneManage/getBymilestone/' + id,
    method: 'get'
  })

export const listMilestoneManagePage = (search) =>
  request({
    url: '/projectManage/milestoneManage/list',
    method: 'post',
    data: search
  })

export const listMilestoneManageAll = (search) =>
  request({
    url: '/projectManage/milestoneManage/listAll',
    method: 'post',
    data: search
  })

export const saveMilestoneManage = (formData) =>
  request({
    url: '/projectManage/milestoneManage/save',
    method: 'post',
    data: formData
  })

export const deleteMilestoneManage = (milestoneManage) =>
  request({
    url: '/projectManage/milestoneManage/delete',
    method: 'post',
    data: milestoneManage
  })

export const bulkInsertMilestoneManage = (milestoneManages) =>
  request({
    url: '/projectManage/milestoneManage/bulkInsert',
    method: 'post',
    data: milestoneManages
  })

export const bulkUpdateMilestoneManage = (milestoneManages) =>
  request({
    url: '/projectManage/milestoneManage/bulkUpdate',
    method: 'post',
    data: milestoneManages
  })

export const bulkDeleteMilestoneManage = (milestoneManages) =>
  request({
    url: '/projectManage/milestoneManage/bulkDelete',
    method: 'post',
    data: milestoneManages
  })

export const checkProgressRate = (data) =>
  request({
    url: '/projectManage/milestoneManage/checkProgressRate',
    method: 'post',
    data: data
  })
