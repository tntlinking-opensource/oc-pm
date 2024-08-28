package com.geeke.team.entity;

import com.geeke.admin.entity.User;
import com.geeke.sys.entity.DictItem;


public class Project {

    private String id;

    private String projectName; // 项目名称

    private String projectShortName; // 项目简称

    private String projectCode; // 项目编号

    private DictItem projectStatus; // 项目状态


    private User projectManager; // 项目经理

    private String planBeginDate; // 计划开始时间

    private String planEndDate; // 计划结束时间


    private Integer memberCount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public DictItem getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(DictItem projectStatus) {
        this.projectStatus = projectStatus;
    }

//    public DictItem getProjectType() {
//        return projectType;
//    }
//
//    public void setProjectType(DictItem projectType) {
//        this.projectType = projectType;
//    }


    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public String getPlanBeginDate() {
        return planBeginDate;
    }

    public void setPlanBeginDate(String planBeginDate) {
        this.planBeginDate = planBeginDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
