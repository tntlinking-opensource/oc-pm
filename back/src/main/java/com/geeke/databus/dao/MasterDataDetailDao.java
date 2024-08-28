package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.MasterDataDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主数据解析数据DAO接口
 * @author
 * @version
 */
@Mapper
public interface MasterDataDetailDao extends CrudDao<MasterDataDetail> {}
