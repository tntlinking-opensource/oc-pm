package com.geeke.documentManage.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.documentManage.entity.DocumentManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文档管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface DocumentManageDao extends CrudDao<DocumentManage> {
    int checkrolebyid(String userid);
}
