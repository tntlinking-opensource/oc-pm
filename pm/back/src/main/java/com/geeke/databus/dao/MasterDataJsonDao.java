package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.MasterDataJson;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主数据批次记录DAO接口
 * @author
 * @version
 */
@Mapper
public interface MasterDataJsonDao extends CrudDao<MasterDataJson> {}
