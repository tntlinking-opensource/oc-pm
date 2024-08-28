package com.geeke.projectManage.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.vo.RateVO;
import com.geeke.projectManage.service.MilestoneManageService;
import com.geeke.requirement.entity.RequirementManage;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
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
 * 里程碑管理Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/projectManage/milestoneManage")
public class MilestoneManageController extends BaseController {

    @Autowired
    private MilestoneManageService milestoneManageService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MilestoneManage entity = milestoneManageService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping("/getBymilestone/{id}")
    public ResponseEntity<JSONObject> getBymilestone(@PathVariable("id") String id) {
        System.out.println("请求参数"+id);
        List<MilestoneManage> result = milestoneManageService.getBymilestone(id);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MilestoneManage> result = milestoneManageService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParamsExp<String> sp) {
        List<MilestoneManage> result = milestoneManageService.listAll(sp.transferPageRequest());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(
        @RequestParam(value = "entity", required = false) String strEntity,
        @RequestParam(value = "annexUploads", required = false) MultipartFile[] annexUploads, // 文件: 附件
        @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        MilestoneManage entity = JSONObject.parseObject(strEntity, MilestoneManage.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = milestoneManageService.save(entity, annexUploads, deleteIds).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MilestoneManage entity) {
        int rows = milestoneManageService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MilestoneManage> entitys) {
        List<String> ids = milestoneManageService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MilestoneManage> entitys) {
        List<String> ids = milestoneManageService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MilestoneManage> entitys) {
        int rows = milestoneManageService.bulkDelete(entitys);
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
        boolean flag = milestoneManageService.checkProgressRate(vo);
        return ResponseEntity.ok(ResultUtil.successJson(flag));
    }

    /**
     * 根据项目id获取所属里程碑 *
     * @param searchParams
     * @return
     */
    @PostMapping(value = "listMap")
    public ResponseEntity<JSONObject> listMap(@RequestBody SearchParamsExp<String> searchParams) {
        List<MilestoneManage> result = milestoneManageService.listAll(searchParams.transferPageRequest());
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(result)) {
            result.forEach(i-> {
                Map<String, Object> map = new HashMap<>();
                map.put("key", i.getId());
                map.put("value", i.getMilestoneName());
                mapList.add(map);
            });
        }
        return ResponseEntity.ok(ResultUtil.successJson(mapList));
    }
}
