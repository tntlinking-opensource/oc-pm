package com.geeke.requirement.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.requirement.entity.RequirementManage;
import com.geeke.requirement.service.RequirementManageService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 需求管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/requirement/requirementManage")
public class RequirementManageController extends BaseController {

    @Autowired
    private RequirementManageService requirementManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        RequirementManage entity = requirementManageService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getByrequire/{id}")
    public ResponseEntity<JSONObject> getByrequires(@PathVariable("id") String id) {
        System.out.println("请求参数"+id);
        List<RequirementManage> result = requirementManageService.getByrequires(id);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> params = searchParams.getParams();
        if (CollectionUtil.isEmpty(params) || params.stream().noneMatch(parameter -> "project_id".equals(parameter.getColumnName()) )){
            //"项目信息不能为空"
            throw  new CommonJsonException(ErrorEnum.E_90004);
        }
        Page<RequirementManage> result = requirementManageService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<RequirementManage> result = requirementManageService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listMilestone")
    public ResponseEntity<JSONObject> listMilestoneAll(@RequestBody SearchParams searchParams) {
        List<MilestoneManage> result = requirementManageService.listMilestoneAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listSchedule")
    public ResponseEntity<JSONObject> listScheduleAll(@RequestBody SearchParams searchParams) {
        List<ScheduleManage> result = requirementManageService.listScheduleAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody RequirementManage entity) {
        String id = requirementManageService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "saveWithFile")
    public ResponseEntity<JSONObject> saveWithFile(
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
            @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws IOException {
        RequirementManage entity = JSONObject.parseObject(strEntity, RequirementManage.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = requirementManageService.save(entity,deleteIds,attachments).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody RequirementManage entity) {
        int rows = requirementManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<RequirementManage> entitys) {
        List<String> ids = requirementManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<RequirementManage> entitys) {
        List<String> ids = requirementManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<RequirementManage> entitys) {
        int rows = requirementManageService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
