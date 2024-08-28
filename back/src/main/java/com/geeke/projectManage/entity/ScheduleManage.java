package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.sys.entity.DictItem;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 日程管理Entity
 * @author
 * @version
 */

public class ScheduleManage extends DataEntity<ScheduleManage> {

    private static final long serialVersionUID = 1740772218121938535L;

    private MilestoneManage milestone; // 里程碑ID

    private String scheduleName; // 日程名称

    private DictItem scheduleStatus; // 日程状态

    private DictItem scheduleType; // 日程类型

    private String planBegin; // 计划开始日期

    private String planEnd; // 计划结束日期

    private String realBegin; // 实际开始日期

    private String realEnd; // 实际结束日期

    private String scheduleDesc; // 日程描述

    private String participants; // 参与人

    private String participantIds;

    public String getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(String participantIds) {
        this.participantIds = participantIds;
    }

    // 构造方法
    public ScheduleManage() {
        super();
    }

    public ScheduleManage(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "里程碑ID不能为空")
    public MilestoneManage getMilestone() {
        return milestone;
    }

    public void setMilestone(MilestoneManage milestone) {
        this.milestone = milestone;
    }

    @Length(min = 1, max = 50, message = "日程名称长度必须介于 1 和 50 之间")
    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    @NotNull(message = "日程状态不能为空")
    public DictItem getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(DictItem scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @NotNull(message = "日程类型不能为空")
    public DictItem getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(DictItem scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getPlanBegin() {
        return planBegin;
    }

    public void setPlanBegin(String planBegin) {
        this.planBegin = planBegin;
    }

    public String getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(String planEnd) {
        this.planEnd = planEnd;
    }

    public String getRealBegin() {
        return realBegin;
    }

    public void setRealBegin(String realBegin) {
        this.realBegin = realBegin;
    }

    public String getRealEnd() {
        return realEnd;
    }

    public void setRealEnd(String realEnd) {
        this.realEnd = realEnd;
    }

    @Length(min = 0, max = 300, message = "日程描述长度必须介于 0 和 300 之间")
    public String getScheduleDesc() {
        return scheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }

    @Length(min = 0, max = 300, message = "参与人长度必须介于 0 和 300 之间")
    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "schedule_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938535";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "日程管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
