package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.AppMasterDataType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用主数据类型配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface AppMasterDataTypeDao extends CrudDao<AppMasterDataType> {}
