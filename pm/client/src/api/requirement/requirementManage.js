import request from '@/utils/request'

export const getRequirementManageById = (id) =>
  request({
    url: '/requirement/requirementManage/' + id,
    method: 'get'
  })

export const getrequireByrid = (id) =>
  request({
    url: '/requirement/requirementManage/getByrequire/' + id,
    method: 'get'
  })

export const listRequirementManagePage = (search) =>
  request({
    url: '/requirement/requirementManage/list',
    method: 'post',
    data: search
  })

export const listRequirementManageAll = (search) =>
  request({
    url: '/requirement/requirementManage/listAll',
    method: 'post',
    data: search
  })

export const listMilestoneManage = (search) =>
  request({
    url: '/requirement/requirementManage/listMilestone',
    method: 'post',
    data: search
  })  
export const listScheduleManage = (search) =>
  request({
    url: '/requirement/requirementManage/listSchedule',
    method: 'post',
    data: search
  })

export const saveRequirementManage = (requirementManage) =>
  request({
    url: '/requirement/requirementManage/save',
    method: 'post',
    data: requirementManage
  })

export const saveRequirementManageWithFile = (requirementManage) =>
  request({
    url: '/requirement/requirementManage/saveWithFile',
    method: 'post',
    data: requirementManage
  })

export const deleteRequirementManage = (requirementManage) =>
  request({
    url: '/requirement/requirementManage/delete',
    method: 'post',
    data: requirementManage
  })

export const bulkInsertRequirementManage = (requirementManages) =>
  request({
    url: '/requirement/requirementManage/bulkInsert',
    method: 'post',
    data: requirementManages
  })

export const bulkUpdateRequirementManage = (requirementManages) =>
  request({
    url: '/requirement/requirementManage/bulkUpdate',
    method: 'post',
    data: requirementManages
  })

export const bulkDeleteRequirementManage = (requirementManages) =>
  request({
    url: '/requirement/requirementManage/bulkDelete',
    method: 'post',
    data: requirementManages
  })
