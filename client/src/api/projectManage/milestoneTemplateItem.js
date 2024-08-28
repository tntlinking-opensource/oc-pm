import request from '@/utils/request'

export const getMilestoneTemplateItemById = (id) =>
  request({
    url: '/projectManage/milestoneTemplateItem/' + id,
    method: 'get'
  })

export const listMilestoneTemplateItemPage = (search) =>
  request({
    url: '/projectManage/milestoneTemplateItem/list',
    method: 'post',
    data: search
  })

export const listMilestoneTemplateItemAll = (search) =>
  request({
    url: '/projectManage/milestoneTemplateItem/listAll',
    method: 'post',
    data: search
  })

export const saveMilestoneTemplateItem = (milestoneTemplateItem) =>
  request({
    url: '/projectManage/milestoneTemplateItem/save',
    method: 'post',
    data: milestoneTemplateItem
  })

export const deleteMilestoneTemplateItem = (milestoneTemplateItem) =>
  request({
    url: '/projectManage/milestoneTemplateItem/delete',
    method: 'post',
    data: milestoneTemplateItem
  })

export const bulkInsertMilestoneTemplateItem = (milestoneTemplateItems) =>
  request({
    url: '/projectManage/milestoneTemplateItem/bulkInsert',
    method: 'post',
    data: milestoneTemplateItems
  })

export const bulkUpdateMilestoneTemplateItem = (milestoneTemplateItems) =>
  request({
    url: '/projectManage/milestoneTemplateItem/bulkUpdate',
    method: 'post',
    data: milestoneTemplateItems
  })

export const bulkDeleteMilestoneTemplateItem = (milestoneTemplateItems) =>
  request({
    url: '/projectManage/milestoneTemplateItem/bulkDelete',
    method: 'post',
    data: milestoneTemplateItems
  })

export const checkProgressRate = (data) =>
  request({
    url: '/projectManage/milestoneTemplateItem/checkProgressRate',
    method: 'post',
    data: data
  })
