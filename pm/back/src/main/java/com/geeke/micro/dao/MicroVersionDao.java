package com.geeke.micro.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.micro.entity.MicroVersion;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用版本管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface MicroVersionDao extends CrudDao<MicroVersion> {}
