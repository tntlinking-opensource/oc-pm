package com.geeke.documentManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 文档管理Entity
 * @author
 * @version
 */

public class DocumentManage extends DataEntity<DocumentManage> {

    private static final long serialVersionUID = 1740772218121938429L;

    private String documentName; // 文档名称

    private String projectId; // 项目ID

    private String milestoneId; // 所属里程碑

    private String scheduleId; // 所属日程

    private String documentType; // 文档类型

    private String demandId; // 关联需求

    private String documentDescription; // 文档描述

    private String annex; // 附件

    // 构造方法
    public DocumentManage() {
        super();
    }

    public DocumentManage(String id) {
        super(id);
    }

    private String projectName;
    private String documenttypeName;
    private String requirementName;
    private String annexName;

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    private String milestoneName;
    private String scheduleName;


    public String getAnnexId() {
        return annexId;
    }

    public void setAnnexId(String annexId) {
        this.annexId = annexId;
    }

    private String annexId;


    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDocumenttypeName() {
        return documenttypeName;
    }

    public void setDocumenttypeName(String documenttypeName) {
        this.documenttypeName = documenttypeName;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }
// 生成get和set方法

    @Length(min = 1, max = 50, message = "文档名称长度必须介于 1 和 50 之间")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Length(min = 0, max = 21, message = "项目ID长度必须介于 0 和 21 之间")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Length(min = 0, max = 21, message = "所属里程碑长度必须介于 0 和 21 之间")
    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    @Length(min = 0, max = 50, message = "所属日程长度必须介于 0 和 50 之间")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Length(min = 0, max = 50, message = "文档类型长度必须介于 0 和 50 之间")
    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Length(min = 0, max = 50, message = "关联需求长度必须介于 0 和 50 之间")
    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    @Length(min = 0, max = 50, message = "文档描述长度必须介于 0 和 50 之间")
    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    @NotNull(message = "附件不能为空")
    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "document_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938429";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "文档管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
