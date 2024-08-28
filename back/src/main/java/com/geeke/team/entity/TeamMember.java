package com.geeke.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 团队管理Entity
 * @author
 * @version
 */

public class TeamMember extends DataEntity<TeamMember> {

    private static final long serialVersionUID = 1740772218121938036L;

    private Project project; // 关联项目id

    private User user;

    private String joinTime; // 加入项目时间

    private String leaveTime; // 离开项目时间

    private DictItem teamRole; // 团队角色id

    private BigDecimal unitPriceCost; // 成本单价

    private BigDecimal planHumanDay; // 计划人天

    private BigDecimal planCost; // 计划成本

    private String memberStatus;


    // 构造方法
    public TeamMember() {
        super();
    }

    public TeamMember(String id) {
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

    @NotNull(message = "关联用户id不能为空")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotNull(message = "加入项目时间不能为空")
    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    @NotNull(message = "离开项目时间不能为空")
    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    @NotNull(message = "团队角色id不能为空")
    public DictItem getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(DictItem teamRole) {
        this.teamRole = teamRole;
    }

    public BigDecimal getUnitPriceCost() {
        return unitPriceCost;
    }

    public void setUnitPriceCost(BigDecimal unitPriceCost) {
        this.unitPriceCost = unitPriceCost;
    }

    @NotNull(message = "计划人天不能为空")
    public BigDecimal getPlanHumanDay() {
        return planHumanDay;
    }

    public void setPlanHumanDay(BigDecimal planHumanDay) {
        this.planHumanDay = planHumanDay;
    }

    public BigDecimal getPlanCost() {
        return planCost;
    }

    public void setPlanCost(BigDecimal planCost) {
        this.planCost = planCost;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "team_member";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938036";
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
}
