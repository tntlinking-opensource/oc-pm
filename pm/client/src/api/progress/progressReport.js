import request from '@/utils/request'

export const getProgressReportById = (id) =>
  request({
    url: '/progress/progressReport/' + id,
    method: 'get'
  })

export const getProjectProgressByProjectId = (projectId) =>
  request({
    url: '/progress/progressReport/projectProgress/' + projectId,
    method: 'get'
  })

export const getMilestoneByProjectId = (projectId) =>
  request({
    url: '/progress/progressReport/milestone/' + projectId,
    method: 'get'
  })

export const listProgressReportPage = (search) =>
  request({
    url: '/progress/progressReport/list',
    method: 'post',
    data: search
  })

export const listProgressReportAll = (search) =>
  request({
    url: '/progress/progressReport/listAll',
    method: 'post',
    data: search
  })

export const deleteProgressReport = (progressReport) =>
  request({
    url: '/progress/progressReport/delete',
    method: 'post',
    data: progressReport
  })

export const getProgressReportByTaskId = (TaskId) =>
  request({
    url: '/progress/progressReport/task/' + TaskId,
    method: 'get'
  })
export const saveDraftProgressReport = (formData) =>
  request({
    url: '/progress/progressReport/saveDraft',
    method: 'post',
    data: formData
  })
export const createProgressReport = (processId, formData) =>
  request({
    url: '/progress/progressReport/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateProgressReport = (taskId, formData) =>
  request({
    url: '/progress/progressReport/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseProgressReport = (taskId) =>
  request({
    url: '/progress/progressReport/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/progress/progressReport/' + taskId + '/listBackActivity',
    method: 'get'
  })
