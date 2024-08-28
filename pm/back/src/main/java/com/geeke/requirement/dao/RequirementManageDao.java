package com.geeke.requirement.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.requirement.entity.RequirementManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface RequirementManageDao extends CrudDao<RequirementManage> {

    List<MilestoneManage> listMilestoneAll(PageRequest pageRequest);

    List<ScheduleManage> listScheduleAll(PageRequest pageRequest);
}
