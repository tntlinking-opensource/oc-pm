package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.MasterDataPushInterfaceRecords;
import com.geeke.databus.service.MasterDataPushInterfaceRecordsService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主数据批次分发记录Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/masterDataPushInterfaceRecords")
public class MasterDataPushInterfaceRecordsController extends BaseController {

    @Autowired
    private MasterDataPushInterfaceRecordsService masterDataPushInterfaceRecordsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MasterDataPushInterfaceRecords entity = masterDataPushInterfaceRecordsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MasterDataPushInterfaceRecords> result = masterDataPushInterfaceRecordsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<MasterDataPushInterfaceRecords> result = masterDataPushInterfaceRecordsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody MasterDataPushInterfaceRecords entity) {
        String id = masterDataPushInterfaceRecordsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MasterDataPushInterfaceRecords entity) {
        int rows = masterDataPushInterfaceRecordsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MasterDataPushInterfaceRecords> entitys) {
        List<String> ids = masterDataPushInterfaceRecordsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MasterDataPushInterfaceRecords> entitys) {
        List<String> ids = masterDataPushInterfaceRecordsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MasterDataPushInterfaceRecords> entitys) {
        int rows = masterDataPushInterfaceRecordsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
