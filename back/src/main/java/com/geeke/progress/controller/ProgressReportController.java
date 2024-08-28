package com.geeke.progress.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.progress.entity.ProgressReport;
import com.geeke.progress.entity.ProjectProgress;
import com.geeke.progress.service.ProgressReportService;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.sys.controller.BaseController;
import com.geeke.team.entity.Project;
import com.geeke.team.service.TeamMemberService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.wf.entity.WfDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static com.geeke.progress.service.ProgressReportService.PROGRESS_SAVED;

/**
 * 进度汇报Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/progress/progressReport")
public class ProgressReportController extends BaseController {

    @Autowired
    private ProgressReportService progressReportService;

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ProgressReport entity = progressReportService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/projectProgress/{projectId}")
    public ResponseEntity<JSONObject> getProjectProgress(@PathVariable("projectId") String projectId) {
        List<ProjectProgress> entity = progressReportService.getProjectProgress(projectId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("milestone/{projectId}")
    public ResponseEntity<JSONObject> getMilestoneByProjectId(@PathVariable("projectId")  String projectId) {
        List<MilestoneManage> entity = progressReportService.getMilestoneByProjectId(projectId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = "listProjectAll")
    public ResponseEntity<JSONObject> listProjectAll(@RequestBody SearchParams searchParams) {
        List<ProjectList> result = progressReportService.listProjectAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> params = setDataPermission(searchParams);
        Page<ProgressReport> result = progressReportService.listPage(
                params,
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    private List<Parameter> setDataPermission(SearchParams searchParams) {
        User userInfo = SessionUtils.getUser();
        if (userInfo ==null){
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        //  除admin角色外，只能查看项目经理为自己的项目汇报
        List<Parameter> params = searchParams.getParams();
        if (!teamMemberService.checkIsAdmin(userInfo.getId())){
            String userId = userInfo.getId();
            params.add(new Parameter("reporter_id", "=", userId));
        }
        params.add(new Parameter("project.project_status", "in", Arrays.asList("2","3","4")));
        return params;
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Parameter> params = setDataPermission(searchParams);
        List<ProgressReport> result = progressReportService.listAll(params, searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ProgressReport entity) {
        String id = entity.getId();
        ProgressReport progressReport = progressReportService.get(id);
        if (progressReport!=null && !PROGRESS_SAVED.equals(progressReport.getProgressStatus())){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90005, "当前汇报无法删除！"));
        }
        int rows = progressReportService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 通过流程任务Id获取业务对象
     * @param taskId
     * @return
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        ProgressReport entity = progressReportService.getByTaskId(taskId);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        int ret = progressReportService.saveDraft(wfDraft, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.create(procDefId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.reapply(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.agree(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.forward(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.commission(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.propose(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.back(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        ActResult actResult = progressReportService.reject(taskId, entity, attachments);
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
        ProgressReport entity = JSONObject.parseObject(strEntity, ProgressReport.class);
        int rows = progressReportService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        ProgressReport entity = progressReportService.getByTaskId(taskId);
        ActResult actResult = progressReportService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @return
     */
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = progressReportService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
}
