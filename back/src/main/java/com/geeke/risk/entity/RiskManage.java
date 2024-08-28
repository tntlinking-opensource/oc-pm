package com.geeke.risk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.SysFile;
import com.geeke.team.entity.Project;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 风险管理Entity
 * @author
 * @version
 */

public class RiskManage extends DataEntity<RiskManage> {

    private static final long serialVersionUID = 1740772218121938312L;

    private String projectId; // 关联项目id

    private String riskName; // 风险名称

    private String riskCode; // 风险编号

    private String milestoneId; // 关联里程碑id

    private String milestoneName; // 里程碑名称

    private String scheduleId; // 关联日程id

    private String scheduleName; // 关联日程名称

    private String riskType; // 风险类型

    private String riskTypeName; // 风险类型名称

    private String riskImpact; // 风险概率

    private String riskImpactName; // 风险概率名称

    private String riskStatus; // 风险状态

    private String riskStatusName; // 风险状态名称

    private String riskText; // 风险内容

    private String riskOption; // 方案

    private String fileId; // 关联文件信息id

    private Project project;

    private List<SysFile> attachmentFile = new ArrayList<>();

    // 构造方法
    public RiskManage() {
        super();
    }

    public RiskManage(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 20, message = "关联项目id长度必须介于 1 和 20 之间")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Length(min = 1, max = 255, message = "风险名称长度必须介于 1 和 255 之间")
    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    @Length(min = 1, max = 20, message = "关联里程碑id长度必须介于 1 和 20 之间")
    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    @Length(min = 0, max = 20, message = "关联日程id长度必须介于 0 和 20 之间")
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Length(min = 1, max = 1, message = "风险类型长度必须介于 1 和 1 之间")
    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    @Length(min = 1, max = 1, message = "风险概率长度必须介于 1 和 1 之间")
    public String getRiskImpact() {
        return riskImpact;
    }

    public void setRiskImpact(String riskImpact) {
        this.riskImpact = riskImpact;
    }

    @Length(min = 1, max = 1, message = "风险状态长度必须介于 1 和 1 之间")
    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    @Length(min = 1, max = 255, message = "风险内容长度必须介于 1 和 255 之间")
    public String getRiskText() {
        return riskText;
    }

    public void setRiskText(String riskText) {
        this.riskText = riskText;
    }

    @Length(min = 0, max = 255, message = "方案长度必须介于 0 和 255 之间")
    public String getRiskOption() {
        return riskOption;
    }

    public void setRiskOption(String riskOption) {
        this.riskOption = riskOption;
    }

    @Length(min = 0, max = 20, message = "关联文件信息id长度必须介于 0 和 20 之间")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<SysFile> getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(List<SysFile> attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

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

    public String getRiskTypeName() {
        return riskTypeName;
    }

    public void setRiskTypeName(String riskTypeName) {
        this.riskTypeName = riskTypeName;
    }

    public String getRiskImpactName() {
        return riskImpactName;
    }

    public void setRiskImpactName(String riskImpactName) {
        this.riskImpactName = riskImpactName;
    }

    public String getRiskStatusName() {
        return riskStatusName;
    }

    public void setRiskStatusName(String riskStatusName) {
        this.riskStatusName = riskStatusName;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "risk_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938312";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "风险管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
