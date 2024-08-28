import request from '@/utils/request'

export const getStandardFieldById = (id) =>
  request({
    url: '/databus/standardField/' + id,
    method: 'get'
  })

export const listStandardFieldPage = (search) =>
  request({
    url: '/databus/standardField/list',
    method: 'post',
    data: search
  })

export const listStandardFieldAll = (search) =>
  request({
    url: '/databus/standardField/listAll',
    method: 'post',
    data: search
  })

export const saveStandardField = (standardField) =>
  request({
    url: '/databus/standardField/save',
    method: 'post',
    data: standardField
  })

export const deleteStandardField = (standardField) =>
  request({
    url: '/databus/standardField/delete',
    method: 'post',
    data: standardField
  })

export const bulkInsertStandardField = (standardFields) =>
  request({
    url: '/databus/standardField/bulkInsert',
    method: 'post',
    data: standardFields
  })

export const bulkUpdateStandardField = (standardFields) =>
  request({
    url: '/databus/standardField/bulkUpdate',
    method: 'post',
    data: standardFields
  })

export const bulkDeleteStandardField = (standardFields) =>
  request({
    url: '/databus/standardField/bulkDelete',
    method: 'post',
    data: standardFields
  })
