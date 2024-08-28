import request from '@/utils/request'

export const getProductionRelationById = (id) =>
  request({
    url: '/databus/productionRelation/' + id,
    method: 'get'
  })

export const listProductionRelationPage = (search) =>
  request({
    url: '/databus/productionRelation/list',
    method: 'post',
    data: search
  })

export const listProductionRelationAll = (search) =>
  request({
    url: '/databus/productionRelation/listAll',
    method: 'post',
    data: search
  })

export const saveProductionRelation = (productionRelation) =>
  request({
    url: '/databus/productionRelation/save',
    method: 'post',
    data: productionRelation
  })

export const deleteProductionRelation = (productionRelation) =>
  request({
    url: '/databus/productionRelation/delete',
    method: 'post',
    data: productionRelation
  })

export const bulkInsertProductionRelation = (productionRelations) =>
  request({
    url: '/databus/productionRelation/bulkInsert',
    method: 'post',
    data: productionRelations
  })

export const bulkUpdateProductionRelation = (productionRelations) =>
  request({
    url: '/databus/productionRelation/bulkUpdate',
    method: 'post',
    data: productionRelations
  })

export const bulkDeleteProductionRelation = (productionRelations) =>
  request({
    url: '/databus/productionRelation/bulkDelete',
    method: 'post',
    data: productionRelations
  })
export const listLedgertypenameAll = (search) =>
  request({
    url: '/databus/productionRelation/ledgertypenameListAll',
    method: 'post',
    data: search
  })
