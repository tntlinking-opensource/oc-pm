package com.geeke.team.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.config.exception.ServiceException;
import com.geeke.sys.controller.BaseController;
import com.geeke.team.entity.Project;
import com.geeke.team.entity.TeamMember;
import com.geeke.team.entity.TeamStat;
import com.geeke.team.service.TeamMemberService;
import com.geeke.utils.DateUtils;
import com.geeke.utils.RemoteResultResolver;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/team/teamMember")
public class TeamMemberController extends BaseController {

    @Autowired
    private TeamMemberService teamMemberService;


    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TeamMember entity = teamMemberService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    @GetMapping("teamStat/{projectId}")
    public ResponseEntity<JSONObject> getTeamStatById(@PathVariable("projectId") String projectId) {
        TeamStat entity = teamMemberService.getTeamStatById(projectId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        if (CollectionUtil.isEmpty(params) || params.stream().noneMatch(parameter -> "project_id".equals(parameter.getColumnName()) )){
            //"项目信息不能为空"
            throw new CommonJsonException(ErrorEnum.E_90004);
        }
        Optional<Parameter> memberStatus = params.stream().filter(parameter -> "leave_time".equals(parameter.getColumnName()) || "join_time".equals(parameter.getColumnName())).findAny();
        memberStatus.ifPresent(parameter -> parameter.setValue(DateUtils.getDate()));
        Page<TeamMember> result = teamMemberService.listPage(
                params,
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        List<TeamMember> members = result.getRows();
        Date date = new Date();
        if (CollectionUtil.isNotEmpty(members)){
            for (TeamMember teamMember : members) {
                Date joinTime = DateUtils.parseDate(teamMember.getJoinTime());
                if (joinTime.compareTo(date) >0 ){
                    teamMember.setMemberStatus("0");
                }

            }
        }

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "projectList", "" })
    public ResponseEntity<JSONObject> projectListPage(@RequestBody SearchParams searchParams) {
        Page<Project> result = teamMemberService.projectListPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TeamMember> result = teamMemberService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "userListAll")
    public ResponseEntity<JSONObject> userListAll(@RequestBody SearchParams searchParams) {
        List<TeamMember> result = teamMemberService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TeamMember entity) {
        int compare = DateUtil.compare(DateUtil.parseDate(entity.getJoinTime()), DateUtil.parseDate(entity.getLeaveTime()));
        if (compare>0){
            //"离开项目时间必须大于加入项目时间"
            throw  new CommonJsonException(ErrorEnum.E_90003);
        }
        String id = teamMemberService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TeamMember entity) {
        String id = entity.getId();
        TeamMember teamMember = teamMemberService.get(id);
        if (teamMember!=null){
            Date date = new Date();
            Date joinTime = DateUtils.parseDate(teamMember.getJoinTime());
            if (joinTime.compareTo(date) <=0 ){
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90005, "选中数据已加入项目，无法删除！"));
            }
        }
        int rows = teamMemberService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TeamMember> entitys) {
        List<String> ids = teamMemberService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TeamMember> entitys) {
        List<String> ids = teamMemberService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TeamMember> entitys) {
        int rows = teamMemberService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
