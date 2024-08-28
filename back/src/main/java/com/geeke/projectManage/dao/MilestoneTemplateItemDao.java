package com.geeke.projectManage.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.projectManage.entity.MilestoneTemplateItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 里程碑模板详情DAO接口
 * @author
 * @version
 */
@Mapper
public interface MilestoneTemplateItemDao extends CrudDao<MilestoneTemplateItem> {
}
