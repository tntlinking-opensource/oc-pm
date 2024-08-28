package com.geeke.projectManage.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.projectManage.entity.MilestoneManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 里程碑管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface MilestoneManageDao extends CrudDao<MilestoneManage> {

    /**
     * 修改里程碑状态
     * @param milestoneId   里程碑ID
     * @param milestoneStatus 里程碑状态
     * @param status    判断实际开始时间\实际结束时间标识
     * @author Yogaa
     * @date 0:42 2023/12/29
     */
    boolean handleMilestoneStatus(@Param("milestoneId") String milestoneId, @Param("milestoneStatus") String milestoneStatus, @Param("status") String status);
}
