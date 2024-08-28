import request from '@/utils/request'

export const getRiskCode = (projectId) =>
  request({
    url: `/risk/riskManage/getRiskCode/${projectId}`,
    method: 'get'
  })

export const listriskManage = (search) =>
  request({
    url: '/risk/riskManage/list',
    method: 'post',
    data: search
  })

export const listRiskManageAll = (search) =>
  request({
    url: '/risk/riskManage/listAll',
    method: 'post',
    data: search
  })

export const listProjectList = (search) =>
  request({
    url: '/projectManage/projectList/listMap',
    method: 'post',
    data: search
  })
export const saveWithFile = (search) =>
  request({
    url: '/risk/riskManage/saveWithFile',
    method: 'post',
    data: search
  })
export const deleteRisk = (search) =>
  request({
    url: '/risk/riskManage/delete',
    method: 'post',
    data: search
  })
//所属里程碑下拉
export const listMilestoneManage = (search) =>
  request({
    url: '/projectManage/milestoneManage/listMap',
    method: 'post',
    data: search
  })
//所属日程下拉
export const listScheduleManage = (search) =>
  request({
    url: '/projectManage/scheduleManage/listMap',
    method: 'post',
    data: search
  })
