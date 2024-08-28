package com.geeke.costBudget.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 成本预算Entity
 * @author
 * @version
 */

public class CostBudget extends DataEntity<CostBudget> {

    private static final long serialVersionUID = 1740772218121938562L;

    private String projectId; // 关联项目id

    private String budgetDate; // 预算日期

    private BigDecimal perInvestBudget; // 人员投入（预算）

    private BigDecimal perInvestReal; // 人员投入（实际）

    private BigDecimal perCostBudget; // 人员成本（预算）

    private BigDecimal perCostReal; // 人员成本（实际）

    private BigDecimal shopPaidBudget; // 采购支出（预算）

    private BigDecimal shopPaidReal; // 采购支出（实际）

    private BigDecimal otherPaidBudget; // 其他支出（预算）

    private BigDecimal otherPaidReal; // 其他支出（实际）

    private BigDecimal totalBudgetAmount; // 小计（预算）
    private BigDecimal totalPaidAmount; // 小计（实际）
    private String diffScale; // 差异比

    // 构造方法
    public CostBudget() {
        super();
        this.perInvestBudget = new BigDecimal(0);
        this.perCostReal = new BigDecimal(0);
        this.shopPaidReal = new BigDecimal(0);
        this.shopPaidBudget = new BigDecimal(0);
        this.otherPaidReal = new BigDecimal(0);
        this.otherPaidBudget = new BigDecimal(0);
        this.totalBudgetAmount = new BigDecimal(0);
        this.totalPaidAmount = new BigDecimal(0);
    }

    public CostBudget(String id) {
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

    @Length(min = 1, max = 20, message = "预算日期长度必须介于 1 和 20 之间")
    public String getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(String budgetDate) {
        this.budgetDate = budgetDate;
    }

    @NotNull(message = "人员投入（预算）不能为空")
    public BigDecimal getPerInvestBudget() {
        return perInvestBudget;
    }

    public void setPerInvestBudget(BigDecimal perInvestBudget) {
        this.perInvestBudget = perInvestBudget;
    }

    @NotNull(message = "人员投入（实际）不能为空")
    public BigDecimal getPerInvestReal() {
        return perInvestReal;
    }

    public void setPerInvestReal(BigDecimal perInvestReal) {
        this.perInvestReal = perInvestReal;
    }

    @NotNull(message = "人员成本（预算）不能为空")
    public BigDecimal getPerCostBudget() {
        return perCostBudget;
    }

    public void setPerCostBudget(BigDecimal perCostBudget) {
        this.perCostBudget = perCostBudget;
    }

    @NotNull(message = "人员成本（实际）不能为空")
    public BigDecimal getPerCostReal() {
        return perCostReal;
    }

    public void setPerCostReal(BigDecimal perCostReal) {
        this.perCostReal = perCostReal;
    }

    @NotNull(message = "采购支出（预算）不能为空")
    public BigDecimal getShopPaidBudget() {
        return shopPaidBudget;
    }

    public void setShopPaidBudget(BigDecimal shopPaidBudget) {
        this.shopPaidBudget = shopPaidBudget;
    }

    @NotNull(message = "采购支出（实际）不能为空")
    public BigDecimal getShopPaidReal() {
        return shopPaidReal;
    }

    public void setShopPaidReal(BigDecimal shopPaidReal) {
        this.shopPaidReal = shopPaidReal;
    }

    @NotNull(message = "其他支出（预算）不能为空")
    public BigDecimal getOtherPaidBudget() {
        return otherPaidBudget;
    }

    public void setOtherPaidBudget(BigDecimal otherPaidBudget) {
        this.otherPaidBudget = otherPaidBudget;
    }

    @NotNull(message = "其他支出（实际）不能为空")
    public BigDecimal getOtherPaidReal() {
        return otherPaidReal;
    }

    public void setOtherPaidReal(BigDecimal otherPaidReal) {
        this.otherPaidReal = otherPaidReal;
    }

    public BigDecimal getTotalBudgetAmount() {
        return totalBudgetAmount;
    }

    public void setTotalBudgetAmount(BigDecimal totalBudgetAmount) {
        this.totalBudgetAmount = totalBudgetAmount;
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getDiffScale() {
        return diffScale;
    }

    public void setDiffScale(String diffScale) {
        this.diffScale = diffScale;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "cost_budget";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938562";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "成本预算表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
