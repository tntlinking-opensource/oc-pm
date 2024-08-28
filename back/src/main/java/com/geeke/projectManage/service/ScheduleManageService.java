package com.geeke.projectManage.service;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.dao.ScheduleManageDao;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.projectManage.entity.StatusConstant;
import com.geeke.projectManage.entity.vo.ScheduleVO;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日程管理Service
 * @author
 * @version
 */

@Service("scheduleManageService")
public class ScheduleManageService extends CrudService<ScheduleManageDao, ScheduleManage> {

    @Autowired
    private ScheduleManageDao scheduleManageDao;

    @Autowired
    private MilestoneManageService milestoneManageService;

    /**
     * 修改日程状态
     * @author Yogaa
     * @date 22:19 2024/1/4
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public boolean modifyScheduleStatus(ScheduleVO vo) {
        boolean flag = scheduleManageDao.modifyScheduleStatus(vo);
        // 处理里程碑状态
        handleMilestoneStatus(vo.getMilestoneId());
        return flag;
    }

    /**
     * 判断里程碑下的日程状态，修改里程碑状态
     * @param milestoneId   里程碑ID
     * @author Yogaa
     * @date 22:21 2024/1/4
     */
    public boolean handleMilestoneStatus(String milestoneId) {
        String targetMilestoneStatus;
        List<ScheduleManage> schedules = getScheduleByMilestoneId(milestoneId);
        // 判断当前里程碑下的日程状态来修改里程碑状态
        Set<String> statusList = schedules.stream().map(schedule -> schedule.getScheduleStatus().getId()).collect(Collectors.toSet());
        // 若日程只有已保存，则修改里程碑状态为已保存
        if (statusList.size() == 1 && statusList.contains(StatusConstant.SCHEDULE_STATUS_SAVED)) {
            targetMilestoneStatus = StatusConstant.MILESTONE_STATUS_SAVED;
        } else if (statusList.size() == 1 && statusList.contains(StatusConstant.SCHEDULE_STATUS_COMPLETED)) {
            // 若日程只有已完成，则修改里程碑状态为已完成
            targetMilestoneStatus = StatusConstant.MILESTONE_STATUS_COMPLETED;
        } else {
            // 其他情况修改里程碑状态为进行中
            targetMilestoneStatus = StatusConstant.MILESTONE_STATUS_PROGRESS;
        }
        return milestoneManageService.handleMilestoneStatus(milestoneId, targetMilestoneStatus);
    }

    public List<ScheduleManage> getScheduleByMilestoneId(String milestoneId) {
        Parameter parameter = new Parameter("milestone_id", "=", milestoneId);
        List<Parameter> parameters = Collections.singletonList(parameter);
        return listAll(parameters, null);
    }
}
