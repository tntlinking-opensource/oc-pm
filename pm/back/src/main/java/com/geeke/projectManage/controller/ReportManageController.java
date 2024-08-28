package com.geeke.projectManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.ReportManage;
import com.geeke.projectManage.service.ReportManageService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 周报管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/projectManage/reportManage")
public class ReportManageController extends BaseController {

    @Autowired
    private ReportManageService reportManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ReportManage entity = reportManageService.getById(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParamsExp<JSONObject> searchParams) {
        Page<ReportManage> result = reportManageService.list(searchParams.transferPageRequest());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<ReportManage> result = reportManageService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(
        @RequestParam(value = "entity", required = false) String strEntity,
        @RequestParam(value = "annexUploads", required = false) MultipartFile[] annexUploads, // 文件: 附件
        @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        ReportManage entity = JSONObject.parseObject(strEntity, ReportManage.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = reportManageService.save(entity, annexUploads, deleteIds).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ReportManage entity) {
        int rows = reportManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<ReportManage> entitys) {
        List<String> ids = reportManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<ReportManage> entitys) {
        List<String> ids = reportManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<ReportManage> entitys) {
        int rows = reportManageService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
