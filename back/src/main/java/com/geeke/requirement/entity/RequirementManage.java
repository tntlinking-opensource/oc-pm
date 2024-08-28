package com.geeke.requirement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.sys.entity.SysFile;
import com.geeke.team.entity.Project;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 需求管理Entity
 * @author
 * @version
 */

public class RequirementManage extends DataEntity<RequirementManage> {

    private static final long serialVersionUID = 1740772218121938278L;

    private Project project; // 关联项目id

    private String requirementCode; // 需求编号
    private String requirementName; // 需求名称

    private MilestoneManage milestone; // 关联里程碑id

    private ScheduleManage schedule; // 关联日程id

    private String proposer; // 需求提出人

    private String submissionDate; // 提出日期

    private String requirementDesc; // 需求描述

    private String fileId; // 需求描述

    private List<SysFile> attachmentFile = new ArrayList<>();



    // 构造方法
    public RequirementManage() {
        super();
    }

    public RequirementManage(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "关联项目id不能为空")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Length(min = 1, max = 255, message = "需求名称长度必须介于 1 和 255 之间")
    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public MilestoneManage getMilestone() {
        return milestone;
    }

    public void setMilestone(MilestoneManage milestone) {
        this.milestone = milestone;
    }

    public ScheduleManage getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleManage schedule) {
        this.schedule = schedule;
    }

    @Length(min = 1, max = 20, message = "需求提出人长度必须介于 1 和 20 之间")
    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    @Length(min = 1, max = -1, message = "提出日期长度必须介于 1 和 -1 之间")
    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Length(min = 1, max = 255, message = "需求描述长度必须介于 1 和 255 之间")
    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
    }

    @NotNull(message = "需求编号不能为空")
    public String getRequirementCode() {
        return requirementCode;
    }

    public void setRequirementCode(String requirementCode) {
        this.requirementCode = requirementCode;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "requirement_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938278";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "团队成员信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }

    public List<SysFile> getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(List<SysFile> attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
