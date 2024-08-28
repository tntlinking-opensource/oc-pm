package com.geeke.projectManage.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.projectManage.entity.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 日程管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface ScheduleManageDao extends CrudDao<ScheduleManage> {

    boolean modifyScheduleStatus(ScheduleVO vo);
}
