package com.geeke.projectManage.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.Role;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.RoleService;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.ReportManage;
import com.geeke.projectManage.service.ProjectListService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.wf.entity.WfDraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 项目清单Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/projectManage/projectList")
public class ProjectListController extends BaseController {

    @Autowired
    private ProjectListService projectListService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ProjectList entity = projectListService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getbyids")
    public ResponseEntity<JSONObject> getByIds( AuthenticationToken authcToken) {

        String loginName = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println("当前用户"+loginName);
        List<ProjectList> result = projectListService.gets(loginName);

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParamsExp<String> sp) {
        Page<ProjectList> result = projectListService.listPage(sp);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParamsExp<String> sp) {
        List<ProjectList> result = projectListService.listAll(sp);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listByTeamMember")
    public ResponseEntity<JSONObject> listByTeamMember(@RequestBody SearchParamsExp<String> sp) {
        List<ProjectList> result = projectListService.listByTeamMember(sp);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    /**
     * 获取所有权限范围内的项目，包含项目下的里程碑
     * @param searchParams  查询参数
     * @author Yogaa
     * @date 16:44 2023/12/27
     */
    @PostMapping(value = "listAllWithMilestone")
    public ResponseEntity<JSONObject> listAllWithMilestone(@RequestBody SearchParamsExp<String> searchParams) {
        List<ProjectList> result = projectListService.listAllWithMilestone(searchParams.transferPageRequest());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ProjectList entity) {
        int rows = projectListService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 通过流程任务Id获取业务对象
     * @param taskId
     * @return
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        ProjectList entity = projectListService.getByTaskId(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    /**
     * 保存草稿
     * @param strDraft 草稿对象
     * @param strEntity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/saveDraft")
    public ResponseEntity<JSONObject> saveDraft(
        @RequestParam(value = "strDraft", required = false) String strDraft,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        int ret = projectListService.saveDraft(wfDraft, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(ret));
    }

    /**
     * 发起申请
     * @param procDefId 流程定义Id
     * @param strEntity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/{procDefId}/create")
    public ResponseEntity<JSONObject> create(
        @PathVariable("procDefId") String procDefId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.create(procDefId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 重新发起申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/{taskId}/reapply")
    public ResponseEntity<JSONObject> reapply(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.reapply(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 同意申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 附件列表
     * @return
     */
    @PostMapping(value = "/{taskId}/agree")
    public ResponseEntity<JSONObject> agree(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.agree(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 转派任务
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/forward")
    public ResponseEntity<JSONObject> forward(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.forward(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 委派任务
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/commission")
    public ResponseEntity<JSONObject> commission(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.commission(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 拟办任务 ，  办理委派过来的任务，把任务转给任务持有者
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/propose")
    public ResponseEntity<JSONObject> propose(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.propose(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/back")
    public ResponseEntity<JSONObject> back(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.back(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/reject")
    public ResponseEntity<JSONObject> reject(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        ActResult actResult = projectListService.reject(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 终止任务的申请流程
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/terminate")
    public ResponseEntity<JSONObject> terminate(
        @PathVariable("taskId") String taskId,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        int rows = projectListService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        ProjectList entity = projectListService.getByTaskId(taskId);
        ActResult actResult = projectListService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @return
     */
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = projectListService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    @PostMapping(value = "listMap")
    public ResponseEntity<JSONObject> listMap(@RequestBody SearchParamsExp<String> searchParams) {
        // admin查看全部审批通过数据，否则展示当前登录人是项目经理的数据
        List<ProjectList> result = projectListService.listMap(searchParams);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(result)) {
            result.forEach(i-> {
                Map<String, Object> map = new HashMap<>();
                map.put("key", i.getId());
                map.put("value", i.getProjectName());
                mapList.add(map);
            });
        }
        return ResponseEntity.ok(ResultUtil.successJson(mapList));
    }

    /**
     * 项目结项验证项目下的里程碑是否都已完成
     * @param id
     * @author Yogaa
     * @date 22:05 2024/1/3
     */
    @GetMapping("/checkCompleted/{id}")
    public ResponseEntity<JSONObject> checkCompleted(@PathVariable String id) {
        boolean flag = projectListService.checkCompleted(id);
        return ResponseEntity.ok(ResultUtil.successJson(flag));
    }

    @PostMapping(value = "saveProjectCompleted")
    public ResponseEntity<JSONObject> saveProjectCompleted(
        @RequestParam(value = "entity", required = false) String strEntity,
        @RequestParam(value = "annexUploads", required = false) MultipartFile[] annexUploads, // 文件: 附件
        @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        ProjectList entity = JSONObject.parseObject(strEntity, ProjectList.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        boolean flag = projectListService.saveProjectCompleted(entity, annexUploads, deleteIds);
        return ResponseEntity.ok(ResultUtil.successJson(flag));
    }
}
