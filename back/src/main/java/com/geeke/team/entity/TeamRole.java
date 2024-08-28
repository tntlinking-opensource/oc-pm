package com.geeke.team.entity;

public class TeamRole {

    private String teamRoleId;

    private String teamRoleName;

    private Integer teamRoleNameCount=0;

    public String getTeamRoleId() {
        return teamRoleId;
    }

    public void setTeamRoleId(String teamRoleId) {
        this.teamRoleId = teamRoleId;
    }

    public String getTeamRoleName() {
        return teamRoleName;
    }

    public void setTeamRoleName(String teamRoleName) {
        this.teamRoleName = teamRoleName;
    }

    public Integer getTeamRoleNameCount() {
        return teamRoleNameCount;
    }

    public void setTeamRoleNameCount(Integer teamRoleNameCount) {
        this.teamRoleNameCount = teamRoleNameCount;
    }
}
