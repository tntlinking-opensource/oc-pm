package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.AppMasterDataType;
import com.geeke.databus.response.AppMasterDataTypeResponse;
import com.geeke.databus.service.AppMasterDataTypeService;
import com.geeke.micro.service.MicroVersionService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用主数据类型配置Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/appMasterDataType")
@RequiredArgsConstructor
public class AppMasterDataTypeController extends BaseController {

    private final AppMasterDataTypeService appMasterDataTypeService;
    private final MicroVersionService microVersionService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        AppMasterDataType entity = appMasterDataTypeService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<AppMasterDataTypeResponse> responsePage = appMasterDataTypeService.listEnhancementPage(searchParams);
        return ResponseEntity.ok(ResultUtil.successJson(responsePage));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<AppMasterDataType> result = appMasterDataTypeService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody AppMasterDataType entity) {
        String id = appMasterDataTypeService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody AppMasterDataType entity) {
        int rows = appMasterDataTypeService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<AppMasterDataType> entitys) {
        List<String> ids = appMasterDataTypeService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<AppMasterDataType> entitys) {
        List<String> ids = appMasterDataTypeService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<AppMasterDataType> entitys) {
        int rows = appMasterDataTypeService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
