import request from '@/utils/request'
export const projectList = (search) =>
  request({
    url: '/projectManage/projectList/list',
    method: 'post',
    data: search
  })
  export const getByProjectId = (projectId) =>
  request({
    url: `/costBudget/getByProjectId/${projectId}`,
    method: 'get',
  })
  export const getByProjectIdWithCheck = (projectId) =>
  request({
    url: `/costBudget/getByProjectIdWithCheck/${projectId}`,
    method: 'get',
  })
  export const bulkSave = (search) =>
  request({
    url: '/costBudget/bulkSave',
    method: 'post',
    data: search
  })
