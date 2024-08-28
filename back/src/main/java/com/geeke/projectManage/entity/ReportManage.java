package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.entity.SysFile;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 周报管理Entity
 * @author
 * @version
 */

public class ReportManage extends DataEntity<ReportManage> {

    private static final long serialVersionUID = 1740772218121938242L;

    private ProjectList project; // 项目ID

    private DictItem reportType; // 报告类型

    private Long reportUser; // 报告人

    private String teamRole; // 项目角色

    private DictItem overtimeType; // 加班类型

    private String reportBegin; // 报告开始时间

    private String reportEnd; // 报告结束时间

    private Double reportDay; // 报告时长

    private String overtimeBegin; // 加班开始时间

    private String overtimeEnd; // 加班结束时间

    private Double overtimeHour; // 加班时长

    private String workContent; // 工作内容

    private String annex; // 附件

    private List<SysFile> annexFiles; // 附件



    public List<SysFile> getAnnexFiles() {
        return annexFiles;
    }

    public void setAnnexFiles(List<SysFile> annexFiles) {
        this.annexFiles = annexFiles;
    }

    // 构造方法
    public ReportManage() {
        super();
    }

    public ReportManage(String id) {
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

    @NotNull(message = "报告类型不能为空")
    public DictItem getReportType() {
        return reportType;
    }

    public void setReportType(DictItem reportType) {
        this.reportType = reportType;
    }

    public Long getReportUser() {
        return reportUser;
    }

    public void setReportUser(Long reportUser) {
        this.reportUser = reportUser;
    }

    public String getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole;
    }

    public DictItem getOvertimeType() {
        return overtimeType;
    }

    public void setOvertimeType(DictItem overtimeType) {
        this.overtimeType = overtimeType;
    }

    @NotNull(message = "报告开始时间不能为空")
    public String getReportBegin() {
        return reportBegin;
    }

    public void setReportBegin(String reportBegin) {
        this.reportBegin = reportBegin;
    }

    @NotNull(message = "报告结束时间不能为空")
    public String getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(String reportEnd) {
        this.reportEnd = reportEnd;
    }

    @NotNull(message = "报告时长不能为空")
    public Double getReportDay() {
        return reportDay;
    }

    public void setReportDay(Double reportDay) {
        this.reportDay = reportDay;
    }

    public String getOvertimeBegin() {
        return overtimeBegin;
    }

    public void setOvertimeBegin(String overtimeBegin) {
        this.overtimeBegin = overtimeBegin;
    }

    public String getOvertimeEnd() {
        return overtimeEnd;
    }

    public void setOvertimeEnd(String overtimeEnd) {
        this.overtimeEnd = overtimeEnd;
    }

    public Double getOvertimeHour() {
        return overtimeHour;
    }

    public void setOvertimeHour(Double overtimeHour) {
        this.overtimeHour = overtimeHour;
    }

    @Length(min = 1, max = 300, message = "工作内容长度必须介于 1 和 300 之间")
    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

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
        return "report_manage";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938242";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "周报管理";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
