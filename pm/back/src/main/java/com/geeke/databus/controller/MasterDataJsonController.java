package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.MasterDataJson;
import com.geeke.databus.service.MasterDataJsonService;
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
 * 主数据批次记录Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/masterDataJson")
public class MasterDataJsonController extends BaseController {

    @Autowired
    private MasterDataJsonService masterDataJsonService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MasterDataJson entity = masterDataJsonService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MasterDataJson> result = masterDataJsonService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<MasterDataJson> result = masterDataJsonService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody MasterDataJson entity) {
        String id = masterDataJsonService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MasterDataJson entity) {
        int rows = masterDataJsonService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MasterDataJson> entitys) {
        List<String> ids = masterDataJsonService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MasterDataJson> entitys) {
        List<String> ids = masterDataJsonService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MasterDataJson> entitys) {
        int rows = masterDataJsonService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
