package com.geeke.team.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.UserRole;
import com.geeke.admin.service.UserRoleService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.service.ProjectListService;
import com.geeke.sys.service.DictItemService;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.team.dao.TeamMemberDao;
import com.geeke.team.entity.*;
import com.geeke.utils.RemoteResultResolver;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 团队管理Service
 * @author
 * @version
 */

@Service("teamMemberService")
@Transactional(readOnly = true)
public class TeamMemberService extends CrudService<TeamMemberDao, TeamMember> {


    @Autowired
    private UserRoleService userRoleService;

    public Page<Project> projectListPage(List<Parameter> params, int offset, int limit, String orderby) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // TODO 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if (!checkIsAdmin(userId)){
            params.add(new Parameter("project_manager", "=", userId));
        }

        params.add(new Parameter("project_status", "in", Arrays.asList("2","3","4")));
        PageRequest pageRequest = new PageRequest(offset, limit, params, orderby);
        int total = dao.projectCount(pageRequest);
        List<Project> list = null;
        if(total > 0) {
            list = dao.projectListPage(pageRequest);
        }
        return new Page<>(total, list);
    }

    public boolean checkIsAdmin(String userId) {
        String adminRoleId = "1861953878566559890";
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("user_id", "=", userId));
        List<UserRole> userRoles = userRoleService.listAll(params, null);
        return userRoles.stream().anyMatch(item -> adminRoleId.equals(item.getRole().getId()));
    }
    public TeamStat getTeamStatById(String id) {
        TeamStat teamStat = new TeamStat();
        List<TeamRole> teamRoleList = dao.listTeamRole();
        List<TeamRole> teamStatRoles = dao.listTeamStatRole(id);
        Project project = dao.projectById(id);
        teamStat.setProjectId(project.getId());
        teamStat.setProjectName(project.getProjectName());
        if (CollectionUtil.isNotEmpty(teamStatRoles)){
            Map<String, Integer> collect = teamStatRoles.stream().collect(Collectors.toMap(TeamRole::getTeamRoleId, TeamRole::getTeamRoleNameCount));
            for (TeamRole teamRole : teamRoleList) {
                teamRole.setTeamRoleNameCount(collect.getOrDefault(teamRole.getTeamRoleId(),0));
            }
        }
        teamStat.setTeamRoleList(teamRoleList);
        return teamStat;
    }
}
