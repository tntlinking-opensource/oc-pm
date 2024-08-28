import request from '@/utils/request'

export const getMilestoneTemplateById = (id) =>
  request({
    url: '/projectManage/milestoneTemplate/' + id,
    method: 'get'
  })

export const listMilestoneTemplatePage = (search) =>
  request({
    url: '/projectManage/milestoneTemplate/list',
    method: 'post',
    data: search
  })

export const listMilestoneTemplateAll = (search) =>
  request({
    url: '/projectManage/milestoneTemplate/listAll',
    method: 'post',
    data: search
  })

export const saveMilestoneTemplate = (milestoneTemplate) =>
  request({
    url: '/projectManage/milestoneTemplate/save',
    method: 'post',
    data: milestoneTemplate
  })

export const deleteMilestoneTemplate = (milestoneTemplate) =>
  request({
    url: '/projectManage/milestoneTemplate/delete',
    method: 'post',
    data: milestoneTemplate
  })

export const bulkInsertMilestoneTemplate = (milestoneTemplates) =>
  request({
    url: '/projectManage/milestoneTemplate/bulkInsert',
    method: 'post',
    data: milestoneTemplates
  })

export const bulkUpdateMilestoneTemplate = (milestoneTemplates) =>
  request({
    url: '/projectManage/milestoneTemplate/bulkUpdate',
    method: 'post',
    data: milestoneTemplates
  })

export const bulkDeleteMilestoneTemplate = (milestoneTemplates) =>
  request({
    url: '/projectManage/milestoneTemplate/bulkDelete',
    method: 'post',
    data: milestoneTemplates
  })
