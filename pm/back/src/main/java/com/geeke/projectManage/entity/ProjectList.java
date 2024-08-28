package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.org.entity.Department;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 项目清单Entity
 * @author
 * @version
 */
public class ProjectList extends WfDataEntity<ProjectList> {

    private static final long serialVersionUID = 1740772218121937934L;

    private String projectName; // 项目名称

    private String projectShortName; // 项目简称

    private String projectCode; // 项目编号

    private DictItem projectStatus; // 项目状态

    private DictItem custom; // 客户

    private Department department; // 部门

    private DictItem projectType; // 项目类型

    private User projectManager; // 项目经理

    private String planBeginDate; // 计划开始时间

    private String planEndDate; // 计划结束时间

    private String applyBy; // 申请人

    private Department applyDepartment; // 申请部门

    private String applyDate; // 申请日期

    private DictItem projectBusiOpp; // 立项商机

    private String projectCoreMember; // 项目核心成员

    private String marketManager; // 市场负责人

    private String customName; // 客户姓名

    private String customRole; // 客户角色

    private BigDecimal projectBudget; // 项目总预算

    private Integer projectBudgetPersonMonth; // 项目计划投入总人月

    private BigDecimal planLaborCost; // 计划投入人力成本

    private String planPersonMonthly; // 单月计划人数

    private String projectDesc; // 项目描述

    private String annex; // 附件

    private List<MilestoneManage> milestones; // 里程碑

    private String realBeginDate; // 实际开始时间

    private String realEndDate; // 实际结束时间

    private String completeDesc; // 完成情况

    private String completeAnnex; // 项目完结附件

    // 实际投入总人月
    private BigDecimal perInvestReal;
    // 实际投入人力成本
    private BigDecimal perCostReal;
    // 项目实际投入=人员成本（实际）+采购支出（实际）+其他支出（实际）
    private BigDecimal totalCostReal;

    public BigDecimal getPerInvestReal() {
        return perInvestReal;
    }

    public void setPerInvestReal(BigDecimal perInvestReal) {
        this.perInvestReal = perInvestReal;
    }

    public BigDecimal getPerCostReal() {
        return perCostReal;
    }

    public void setPerCostReal(BigDecimal perCostReal) {
        this.perCostReal = perCostReal;
    }

    public BigDecimal getTotalCostReal() {
        return totalCostReal;
    }

    public void setTotalCostReal(BigDecimal totalCostReal) {
        this.totalCostReal = totalCostReal;
    }

    public String getRealBeginDate() {
        return realBeginDate;
    }

    public void setRealBeginDate(String realBeginDate) {
        this.realBeginDate = realBeginDate;
    }

    public String getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(String realEndDate) {
        this.realEndDate = realEndDate;
    }

    public String getCompleteDesc() {
        return completeDesc;
    }

    public void setCompleteDesc(String completeDesc) {
        this.completeDesc = completeDesc;
    }

    public String getCompleteAnnex() {
        return completeAnnex;
    }

    public void setCompleteAnnex(String completeAnnex) {
        this.completeAnnex = completeAnnex;
    }

    public List<MilestoneManage> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<MilestoneManage> milestones) {
        this.milestones = milestones;
    }

    // 构造方法
    public ProjectList() {
        super();
    }

    public ProjectList(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 50, message = "项目名称长度必须介于 1 和 50 之间")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Length(min = 1, max = 30, message = "项目简称长度必须介于 1 和 30 之间")
    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    @Length(min = 1, max = 14, message = "项目编号长度必须介于 1 和 14 之间")
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @NotNull(message = "项目状态不能为空")
    public DictItem getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(DictItem projectStatus) {
        this.projectStatus = projectStatus;
    }

    @NotNull(message = "客户不能为空")
    public DictItem getCustom() {
        return custom;
    }

    public void setCustom(DictItem custom) {
        this.custom = custom;
    }

    @NotNull(message = "部门不能为空")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @NotNull(message = "项目类型不能为空")
    public DictItem getProjectType() {
        return projectType;
    }

    public void setProjectType(DictItem projectType) {
        this.projectType = projectType;
    }

    @NotNull(message = "项目经理不能为空")
    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    @Length(min = 1, max = -1, message = "计划开始时间长度必须介于 1 和 -1 之间")
    public String getPlanBeginDate() {
        return planBeginDate;
    }

    public void setPlanBeginDate(String planBeginDate) {
        this.planBeginDate = planBeginDate;
    }

    @Length(min = 1, max = -1, message = "计划结束时间长度必须介于 1 和 -1 之间")
    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    @Length(min = 1, max = 64, message = "申请人长度必须介于 1 和 64 之间")
    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    @NotNull(message = "申请部门不能为空")
    public Department getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(Department applyDepartment) {
        this.applyDepartment = applyDepartment;
    }

    @Length(min = 1, max = -1, message = "申请日期长度必须介于 1 和 -1 之间")
    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    @NotNull(message = "立项商机不能为空")
    public DictItem getProjectBusiOpp() {
        return projectBusiOpp;
    }

    public void setProjectBusiOpp(DictItem projectBusiOpp) {
        this.projectBusiOpp = projectBusiOpp;
    }

    @Length(min = 1, max = 10, message = "项目核心成员长度必须介于 1 和 10 之间")
    public String getProjectCoreMember() {
        return projectCoreMember;
    }

    public void setProjectCoreMember(String projectCoreMember) {
        this.projectCoreMember = projectCoreMember;
    }

    @Length(min = 1, max = 10, message = "市场负责人长度必须介于 1 和 10 之间")
    public String getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(String marketManager) {
        this.marketManager = marketManager;
    }

    @Length(min = 0, max = 10, message = "客户姓名长度必须介于 0 和 10 之间")
    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Length(min = 0, max = 10, message = "客户角色长度必须介于 0 和 10 之间")
    public String getCustomRole() {
        return customRole;
    }

    public void setCustomRole(String customRole) {
        this.customRole = customRole;
    }

    @NotNull(message = "项目总预算不能为空")
    public BigDecimal getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(BigDecimal projectBudget) {
        this.projectBudget = projectBudget;
    }

    @NotNull(message = "项目计划投入总人月不能为空")
    public Integer getProjectBudgetPersonMonth() {
        return projectBudgetPersonMonth;
    }

    public void setProjectBudgetPersonMonth(Integer projectBudgetPersonMonth) {
        this.projectBudgetPersonMonth = projectBudgetPersonMonth;
    }

    @NotNull(message = "计划投入人力成本不能为空")
    public BigDecimal getPlanLaborCost() {
        return planLaborCost;
    }

    public void setPlanLaborCost(BigDecimal planLaborCost) {
        this.planLaborCost = planLaborCost;
    }

    @Length(min = 0, max = 300, message = "单月计划人数长度必须介于 0 和 300 之间")
    public String getPlanPersonMonthly() {
        return planPersonMonthly;
    }

    public void setPlanPersonMonthly(String planPersonMonthly) {
        this.planPersonMonthly = planPersonMonthly;
    }

    @Length(min = 0, max = 300, message = "项目描述长度必须介于 0 和 300 之间")
    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    @Length(min = 0, max = 300, message = "附件长度必须介于 0 和 300 之间")
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
        return "project_list";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121937934";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "项目清单";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }


}
