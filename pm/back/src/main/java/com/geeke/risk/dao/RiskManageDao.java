package com.geeke.risk.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.risk.entity.RiskManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 风险管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface RiskManageDao extends CrudDao<RiskManage> {}
