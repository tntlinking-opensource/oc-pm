package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.MasterDataPushInterfaceRecords;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主数据批次分发记录DAO接口
 * @author
 * @version
 */
@Mapper
public interface MasterDataPushInterfaceRecordsDao extends CrudDao<MasterDataPushInterfaceRecords> {}
