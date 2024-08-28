package com.geeke.costBudget.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.costBudget.entity.CostBudget;
import com.geeke.costBudget.entity.vo.CostBudgetVo;
import com.geeke.costBudget.service.CostBudgetService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 成本预算Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/costBudget")
public class CostBudgetController extends BaseController {

    @Autowired
    private CostBudgetService costBudgetService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        CostBudget entity = costBudgetService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getByProjectId/{projectId}")
    public ResponseEntity<JSONObject> getByProjectId(@PathVariable("projectId") String projectId) throws ParseException {
        CostBudgetVo entity = costBudgetService.getByProjectId(projectId);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getByProjectIdWithCheck/{projectId}")
    public ResponseEntity<JSONObject> getByProjectIdWithCheck(@PathVariable("projectId") String projectId) throws ParseException {
        CostBudgetVo entity = costBudgetService.getByProjectIdWithCheck(projectId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<CostBudget> result = costBudgetService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<CostBudget> result = costBudgetService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody CostBudget entity) {
        String id = costBudgetService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody CostBudget entity) {
        int rows = costBudgetService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<CostBudget> entitys) {
        List<String> ids = costBudgetService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<CostBudget> entitys) {
        List<String> ids = costBudgetService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<CostBudget> entitys) {
        int rows = costBudgetService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkSave")
    public ResponseEntity<JSONObject> bulkSave(@RequestBody List<CostBudget> entitys) {
        List<String> ids = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(entitys)){
            if(StringUtils.isNotBlank(entitys.get(0).getId())){
                ids = costBudgetService.bulkUpdate(entitys);
            } else {
                ids = costBudgetService.bulkInsert(entitys);
            }
        }
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
}
