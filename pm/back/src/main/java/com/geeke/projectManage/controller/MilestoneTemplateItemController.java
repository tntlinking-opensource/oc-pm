package com.geeke.projectManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.projectManage.entity.MilestoneTemplateItem;
import com.geeke.projectManage.entity.vo.RateVO;
import com.geeke.projectManage.service.MilestoneTemplateItemService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * 里程碑模板详情Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/projectManage/milestoneTemplateItem")
public class MilestoneTemplateItemController extends BaseController {

    @Autowired
    private MilestoneTemplateItemService milestoneTemplateItemService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MilestoneTemplateItem entity = milestoneTemplateItemService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MilestoneTemplateItem> result = milestoneTemplateItemService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<MilestoneTemplateItem> result = milestoneTemplateItemService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody MilestoneTemplateItem entity) {
        String id = milestoneTemplateItemService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MilestoneTemplateItem entity) {
        int rows = milestoneTemplateItemService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MilestoneTemplateItem> entitys) {
        List<String> ids = milestoneTemplateItemService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MilestoneTemplateItem> entitys) {
        List<String> ids = milestoneTemplateItemService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MilestoneTemplateItem> entitys) {
        int rows = milestoneTemplateItemService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 验证里程碑进度总和是否超过100%
     * @param vo    参数
     * @author Yogaa
     * @date 11:40 2023/12/29
     */
    @PostMapping("/checkProgressRate")
    public ResponseEntity<JSONObject> checkProgressRate(@RequestBody RateVO vo) {
        boolean flag = milestoneTemplateItemService.checkProgressRate(vo);
        return ResponseEntity.ok(ResultUtil.successJson(flag));
    }
}
