package com.geeke.projectManage.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.projectManage.entity.vo.ScheduleVO;
import com.geeke.projectManage.service.ScheduleManageService;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 日程管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/projectManage/scheduleManage")
public class ScheduleManageController extends BaseController {

    @Autowired
    private ScheduleManageService scheduleManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ScheduleManage entity = scheduleManageService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getscheduleByid/{id}")
    public ResponseEntity<JSONObject> getscheduleByid(@PathVariable("id") String id) {
        System.out.println("请求参数"+id);
        List<ScheduleManage> result = scheduleManageService.getscheduleByid(id);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<ScheduleManage> result = scheduleManageService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<ScheduleManage> result = scheduleManageService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody ScheduleManage entity) {
        String id = scheduleManageService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ScheduleManage entity) {
        int rows = scheduleManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<ScheduleManage> entitys) {
        List<String> ids = scheduleManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<ScheduleManage> entitys) {
        List<String> ids = scheduleManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<ScheduleManage> entitys) {
        int rows = scheduleManageService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @PostMapping(value = "modifyScheduleStatus")
    public ResponseEntity<JSONObject> modifyScheduleStatus(@RequestBody ScheduleVO vo) {
        boolean flag = scheduleManageService.modifyScheduleStatus(vo);
        return ResponseEntity.ok(ResultUtil.successJson(flag));
    }

    /**
     * 根据里程碑id获取所属日程 *
     * @param searchParams
     * @return
     */
    @PostMapping(value = "listMap")
    public ResponseEntity<JSONObject> listMap(@RequestBody SearchParamsExp<String> searchParams) {
        List<ScheduleManage> result = scheduleManageService.listAll(searchParams.transferPageRequest());
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(result)) {
            result.forEach(i-> {
                Map<String, Object> map = new HashMap<>();
                map.put("key", i.getId());
                map.put("value", i.getScheduleName());
                mapList.add(map);
            });
        }
        return ResponseEntity.ok(ResultUtil.successJson(mapList));
    }
}
