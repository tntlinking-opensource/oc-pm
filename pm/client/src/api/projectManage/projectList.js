import request from '@/utils/request'

export const getProjectListById = (id) =>
  request({
    url: '/projectManage/projectList/' + id,
    method: 'get'
  })

export const listProjectListPage = (search) =>
  request({
    url: '/projectManage/projectList/list',
    method: 'post',
    data: search
  })

export const getProjectListByIds = () =>
  request({
    url: '/projectManage/projectList/getbyids',
    method: 'get'
  })

export const checkCompleted = (id) =>
  request({
    url: '/projectManage/projectList/checkCompleted/' + id,
    method: 'get'
  })

export const listProjectListAll = (search) =>
  request({
    url: '/projectManage/projectList/listAll',
    method: 'post',
    data: search
  })

export const listProjectListByTeamMember = (search) =>
  request({
    url: '/projectManage/projectList/listByTeamMember',
    method: 'post',
    data: search
  })

export const listProjectListAllWithMilestone = (search) =>
  request({
    url: '/projectManage/projectList/listAllWithMilestone',
    method: 'post',
    data: search
  })

export const deleteProjectList = (projectList) =>
  request({
    url: '/projectManage/projectList/delete',
    method: 'post',
    data: projectList
  })

export const getProjectListByTaskId = (TaskId) =>
  request({
    url: '/projectManage/projectList/task/' + TaskId,
    method: 'get'
  })
export const saveDraftProjectList = (formData) =>
  request({
    url: '/projectManage/projectList/saveDraft',
    method: 'post',
    data: formData
  })
export const createProjectList = (processId, formData) =>
  request({
    url: '/projectManage/projectList/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateProjectList = (taskId, formData) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseProjectList = (taskId) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/projectManage/projectList/' + taskId + '/listBackActivity',
    method: 'get'
  })

export const saveProjectCompleted = (formData) =>
  request({
    url: '/projectManage/projectList/saveProjectCompleted',
    method: 'post',
    data: formData
  })
