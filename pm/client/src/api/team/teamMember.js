import request from '@/utils/request'



export const getTeamMemberStat = (id) =>
  request({
    url: '/team/teamMember/teamStat/' + id,
    method: 'get'
  })

export const getTeamMemberById = (id) =>
  request({
    url: '/team/teamMember/' + id,
    method: 'get'
  })

export const listTeamMemberPage = (search) =>
  request({
    url: '/team/teamMember/list',
    method: 'post',
    data: search
  })

export const listProjectPage = (search) =>
  request({
    url: '/team/teamMember/projectList',
    method: 'post',
    data: search
  })

export const listTeamMemberAll = (search) =>
  request({
    url: '/team/teamMember/listAll',
    method: 'post',
    data: search
  })

export const listUserAll = (search) =>
  request({
    url: '/team/teamMember/userListAll',
    method: 'post',
    data: search
  })

export const saveTeamMember = (teamMember) =>
  request({
    url: '/team/teamMember/save',
    method: 'post',
    data: teamMember
  })

export const deleteTeamMember = (teamMember) =>
  request({
    url: '/team/teamMember/delete',
    method: 'post',
    data: teamMember
  })

export const bulkInsertTeamMember = (teamMembers) =>
  request({
    url: '/team/teamMember/bulkInsert',
    method: 'post',
    data: teamMembers
  })

export const bulkUpdateTeamMember = (teamMembers) =>
  request({
    url: '/team/teamMember/bulkUpdate',
    method: 'post',
    data: teamMembers
  })

export const bulkDeleteTeamMember = (teamMembers) =>
  request({
    url: '/team/teamMember/bulkDelete',
    method: 'post',
    data: teamMembers
  })
