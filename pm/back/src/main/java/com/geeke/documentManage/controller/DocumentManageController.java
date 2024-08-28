package com.geeke.documentManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.documentManage.entity.DocumentManage;
import com.geeke.documentManage.service.DocumentManageService;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.ProjectList;
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
 * 文档管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/documentManage/documentManage")
public class DocumentManageController extends BaseController {

    @Autowired
    private DocumentManageService documentManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        DocumentManage entity = documentManageService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    /*@PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<DocumentManage> result = documentManageService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }*/

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParamsExp<String> sp) {
        Page<DocumentManage> result = documentManageService.listPage(sp);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    /*@PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<DocumentManage> result = documentManageService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }*/

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParamsExp<String> sp) {
        List<DocumentManage> result = documentManageService.listAll(sp);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(
        @RequestParam(value = "entity", required = false) String strEntity,
        @RequestParam(value = "annexUploads", required = false) MultipartFile[] annexUploads, // 文件: 附件
        @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        DocumentManage entity = JSONObject.parseObject(strEntity, DocumentManage.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = documentManageService.save(entity, annexUploads, deleteIds).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody DocumentManage entity) {
        int rows = documentManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<DocumentManage> entitys) {
        List<String> ids = documentManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<DocumentManage> entitys) {
        List<String> ids = documentManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<DocumentManage> entitys) {
        int rows = documentManageService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
