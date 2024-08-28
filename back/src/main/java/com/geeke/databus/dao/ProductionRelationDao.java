package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.ProductionRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产消费关系DAO接口
 * @author
 * @version
 */
@Mapper
public interface ProductionRelationDao extends CrudDao<ProductionRelation> {
    public ProductionRelation getByMasterIdAndtenantId(String tenantId,String masterDataTypeId);
    public int deleteByMasterIdAndtenantId(ProductionRelation entity);
}
