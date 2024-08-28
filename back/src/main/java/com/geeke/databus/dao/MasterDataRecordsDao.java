package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.MasterDataDetail;
import com.geeke.databus.entity.MasterDataRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 主数据批次记录DAO接口
 * @author
 * @version
 */
@Mapper
public interface MasterDataRecordsDao extends CrudDao<MasterDataRecords> {
    public List<MasterDataDetail> getNewData(String tenantId, String id);
}
