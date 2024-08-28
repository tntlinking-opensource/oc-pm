package com.geeke.projectManage.entity.vo;

public class ScheduleVO {

    /**
     * 日程ID，多个用,分隔
     */
    private String ids;

    /**
     * 目标状态
     */
    private String status;

    private String milestoneId;

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
