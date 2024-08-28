package com.geeke.micro.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.micro.entity.MicroApp;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface MicroAppDao extends CrudDao<MicroApp> {}
