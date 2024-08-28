package com.geeke.databus.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.databus.entity.StandardField;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台主数据DAO接口
 * @author
 * @version
 */
@Mapper
public interface StandardFieldDao extends CrudDao<StandardField> {}
