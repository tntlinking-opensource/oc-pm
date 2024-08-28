package com.geeke.risk.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.requirement.entity.RequirementManage;
import com.geeke.risk.entity.RiskManage;
import com.geeke.risk.service.RiskManageService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 风险管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/risk/riskManage")
public class RiskManageController extends BaseController {

    @Autowired
    private RiskManageService riskManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        RiskManage entity = riskManageService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<RiskManage> result = riskManageService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        result = new Page<>(result.getTotal(), riskManageService.entityListToDtoList(result.getRows()));

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<RiskManage> result = riskManageService.listAll(searchParams.getParams(), searchParams.getOrderby());
        result = riskManageService.entityListToDtoList(result);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody RiskManage entity) {
        String id = riskManageService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "saveWithFile")
    public ResponseEntity<JSONObject> saveWithFile(
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
            @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws IOException {
        RiskManage entity = JSONObject.parseObject(strEntity, RiskManage.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = riskManageService.save(entity,deleteIds,attachments).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody RiskManage entity) {
        int rows = riskManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<RiskManage> entitys) {
        List<String> ids = riskManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<RiskManage> entitys) {
        List<String> ids = riskManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<RiskManage> entitys) {
        int rows = riskManageService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @GetMapping("/getRiskCode/{projectId}")
    public ResponseEntity<JSONObject> getProjectCodeAndRiskCode(@PathVariable("projectId") String projectId) {
        Map<String, Object> map = riskManageService.getRiskCode(projectId);
        return ResponseEntity.ok(ResultUtil.successJson(map));
    }

}
