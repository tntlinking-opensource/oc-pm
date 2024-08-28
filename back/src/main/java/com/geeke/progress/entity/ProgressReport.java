package com.geeke.progress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 进度汇报Entity
 * @author
 * @version
 */
public class ProgressReport extends WfDataEntity<ProgressReport> {

    private static final long serialVersionUID = 1740772218121938609L;

    private ProjectList project; // 项目id

    private User reporter; // 汇报人

    private MilestoneManage milestone; // 里程碑id

    private String milestoneProgress; // 当前汇报里程碑进度

    private String progressDesc; // 进度描述

    private String issuesAndReasons; // 存在的问题以及原因

    private String solution; // 问题解决方案

    private String nextPhasePlan; // 下一阶段工作计划

    private String annex; // 附件

    private Integer progressStatus; // 汇报状态

    private Double totalRate; // 总进度

    private String reportingTheme; // 汇报主题

    private List<ProjectProgress> projectProgressList;

    public List<ProjectProgress> getProjectProgressList() {
        return projectProgressList;
    }

    public void setProjectProgressList(List<ProjectProgress> projectProgressList) {
        this.projectProgressList = projectProgressList;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    // 构造方法
    public ProgressReport() {
        super();
    }

    public ProgressReport(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "项目id不能为空")
    public ProjectList getProject() {
        return project;
    }

    public void setProject(ProjectList project) {
        this.project = project;
    }

    @NotNull(message = "里程碑id不能为空")
    public MilestoneManage getMilestone() {
        return milestone;
    }

    public Double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Double totalRate) {
        this.totalRate = totalRate;
    }

    public void setMilestone(MilestoneManage milestone) {
        this.milestone = milestone;
    }

    @NotNull(message = "当前汇报里程碑进度不能为空")
    public String getMilestoneProgress() {
        return milestoneProgress;
    }

    public void setMilestoneProgress(String milestoneProgress) {
        this.milestoneProgress = milestoneProgress;
    }

    @Length(min = 1, max = 300, message = "进度描述长度必须介于 1 和 300 之间")
    public String getProgressDesc() {
        return progressDesc;
    }

    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }

    @Length(min = 0, max = 300, message = "存在的问题以及原因长度必须介于 0 和 300 之间")
    public String getIssuesAndReasons() {
        return issuesAndReasons;
    }

    public void setIssuesAndReasons(String issuesAndReasons) {
        this.issuesAndReasons = issuesAndReasons;
    }

    @Length(min = 0, max = 300, message = "问题解决方案长度必须介于 0 和 300 之间")
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Length(min = 1, max = 300, message = "下一阶段工作计划长度必须介于 1 和 300 之间")
    public String getNextPhasePlan() {
        return nextPhasePlan;
    }

    public void setNextPhasePlan(String nextPhasePlan) {
        this.nextPhasePlan = nextPhasePlan;
    }

    @Length(min = 0, max = 300, message = "附件长度必须介于 0 和 300 之间")
    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public Integer getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(Integer progressStatus) {
        this.progressStatus = progressStatus;
    }

    @Length(min = 0, max = 255, message = "汇报主题长度必须介于 0 和 255 之间")
    public String getReportingTheme() {
        return reportingTheme;
    }

    public void setReportingTheme(String reportingTheme) {
        this.reportingTheme = reportingTheme;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "progress_report";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938609";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "进度汇报";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
