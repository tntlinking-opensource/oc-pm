package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.StandardField;
import com.geeke.databus.service.StandardFieldService;
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
 * 平台主数据Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/standardField")
public class StandardFieldController extends BaseController {

    @Autowired
    private StandardFieldService standardFieldService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        StandardField entity = standardFieldService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<StandardField> result = standardFieldService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<StandardField> result = standardFieldService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody StandardField entity) {

        String id = standardFieldService.save(entity).getId();

        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody StandardField entity) {
        int rows = standardFieldService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<StandardField> entitys) {
        List<String> ids = standardFieldService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<StandardField> entitys) {
        List<String> ids = standardFieldService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<StandardField> entitys) {
        int rows = standardFieldService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
