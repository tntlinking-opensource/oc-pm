package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.MasterDataPushRecords;
import com.geeke.databus.service.MasterDataPushRecordsService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.RemoteResultResolver;
import com.geeke.utils.ResultUtil;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@RequestMapping(value = "/databus/masterDataPushRecords")
public class MasterDataPushRecordsController extends BaseController {

    @Autowired
    private MasterDataPushRecordsService masterDataPushRecordsService;

    @Autowired
    private DynamicRestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MasterDataPushRecords entity = masterDataPushRecordsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MasterDataPushRecords> result = masterDataPushRecordsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<MasterDataPushRecords> result = masterDataPushRecordsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "targetAppListAll")
    public ResponseEntity<JSONObject> targetAppListAll(@RequestBody SearchParams searchParams) {
        //查看是否多次调用
        int flag = RemoteResultResolver.getRemoteFlag();
        //设置Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(RemoteResultResolver.REMOTE_FLAG, String.valueOf(flag++));
        JSONObject listAll = restTemplate.post(
            "cloud-geeke-devtool",
            "/micro/microVersion/listAll",
            JSONObject.class,
            searchParams,
            headers
        );
        return ResponseEntity.ok(ResultUtil.successJson(listAll.get("data")));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody MasterDataPushRecords entity) {
        String id = masterDataPushRecordsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MasterDataPushRecords entity) {
        int rows = masterDataPushRecordsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MasterDataPushRecords> entitys) {
        List<String> ids = masterDataPushRecordsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MasterDataPushRecords> entitys) {
        List<String> ids = masterDataPushRecordsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MasterDataPushRecords> entitys) {
        int rows = masterDataPushRecordsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
