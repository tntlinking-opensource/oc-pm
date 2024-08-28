package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.sys.entity.DictItem;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 里程碑管理Entity
 * @author
 * @version
 */

public class MilestoneManage extends DataEntity<MilestoneManage> {

    private static final long serialVersionUID = 1740772218121938361L;

    private ProjectList project; // 项目ID

    private String milestoneName; // 里程碑名称

    private DictItem milestoneStatus; // 状态

    private String planBegin; // 计划开始日期

    private String planEnd; // 计划结束日期

    private String realBegin; // 实际开始日期

    private String realEnd; // 实际结束日期

    private Integer progressRate; // 进度占比

    private String participants; // 参与人

    private String participantIds;

    private String milestoneDesc; // 描述

    private Integer sort; // 顺序

    private String annex; // 附件

    private String annexName; // 附件名称

    private Integer scheduleCnt; // 日程数量

    private Integer scheduleCompletedCnt; // 日程数量（已完成）

    public Integer getScheduleCompletedCnt() {
        return scheduleCompletedCnt;
    }

    public void setScheduleCompletedCnt(Integer scheduleCompletedCnt) {
        this.scheduleCompletedCnt = scheduleCompletedCnt;
    }

    public Integer getScheduleCnt() {
        return scheduleCnt;
    }

    public void setScheduleCnt(Integer scheduleCnt) {
        this.scheduleCnt = scheduleCnt;
    }

    // 构造方法
    public MilestoneManage() {
        super();
    }

    public MilestoneManage(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "项目ID不能为空")
    public ProjectList getProject() {
        return project;
    }

    public void setProject(ProjectList project) {
        this.project = project;
    }

    @Length(min = 1, max = 50, message = "里程碑名称长度必须介于 1 和 50 之间")
    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    @NotNull(message = "状态不能为空")
    public DictItem getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(DictItem milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
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

    @NotNull(message = "进度占比不能为空")
    public Integer getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(Integer progressRate) {
        this.progressRate = progressRate;
    }

    @Length(min = 0, max = 300, message = "参与人长度必须介于 0 和 300 之间")
    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(String participantIds) {
        this.participantIds = participantIds;
    }

    @Length(min = 0, max = 300, message = "描述长度必须介于 0 和 300 之间")
    public String getMilestoneDesc() {
        return milestoneDesc;
    }

    public void setMilestoneDesc(String milestoneDesc) {
        this.milestoneDesc = milestoneDesc;
    }

    @NotNull(message = "顺序不能为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "milestone_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938361";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "里程碑管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
