package com.geeke.costBudget.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.costBudget.entity.CostBudget;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 成本预算Entity
 * @author
 * @version
 */

public class CostBudgetVo {
    private String projectName; // 项目名称
    private String startAndEndDate; // 开始日期和结束日期
    private BigDecimal projectBudget; // 可支配预算金额
    private BigDecimal allBudgetAmount; // 已预算金额
    private BigDecimal allPaidAmount; // 实际支出金额

    private List<CostBudget>  costBudgetList;

    public List<CostBudget> getCostBudgetList() {
        return costBudgetList;
    }

    public void setCostBudgetList(List<CostBudget> costBudgetList) {
        this.costBudgetList = costBudgetList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartAndEndDate() {
        return startAndEndDate;
    }

    public void setStartAndEndDate(String startAndEndDate) {
        this.startAndEndDate = startAndEndDate;
    }

    public BigDecimal getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(BigDecimal projectBudget) {
        this.projectBudget = projectBudget;
    }

    public BigDecimal getAllBudgetAmount() {
        return allBudgetAmount;
    }

    public void setAllBudgetAmount(BigDecimal allBudgetAmount) {
        this.allBudgetAmount = allBudgetAmount;
    }

    public BigDecimal getAllPaidAmount() {
        return allPaidAmount;
    }

    public void setAllPaidAmount(BigDecimal allPaidAmount) {
        this.allPaidAmount = allPaidAmount;
    }
}
