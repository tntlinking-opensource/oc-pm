package com.geeke.team.entity;


import java.util.List;

public class TeamStat {

    private String projectId;

    private String projectName;

    private List<TeamRole> teamRoleList;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<TeamRole> getTeamRoleList() {
        return teamRoleList;
    }

    public void setTeamRoleList(List<TeamRole> teamRoleList) {
        this.teamRoleList = teamRoleList;
    }
}
