package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.service.MasterDataBatchRecordsService;
import com.geeke.micro.entity.MicroVersion;
import com.geeke.micro.service.MicroVersionService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 主数据批次记录Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/masterDataBatchRecords")
public class MasterDataBatchRecordsController extends BaseController {

    @Autowired
    private MasterDataBatchRecordsService masterDataBatchRecordsService;
    @Autowired
    private DynamicRestTemplate restTemplate;
    @Autowired
    private MicroVersionService microVersionService;
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        MasterDataBatchRecords entity = masterDataBatchRecordsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<MasterDataBatchRecords> result = masterDataBatchRecordsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        List<MasterDataBatchRecords> rows = result.getRows();
        SearchParams microVersionParam = new SearchParams();
        List<MicroVersion> microVersions = microVersionService.listAll(microVersionParam.getParams(), microVersionParam.getOrderby());


            for (MasterDataBatchRecords row : rows) {
                String[] split = row.getTargetAppId().split(",");
                String TargetAppIdName = "";
                for (String s : split) {
                    for (MicroVersion microVersion : microVersions) {
                        if (microVersion.getId().equals(s)) {
                            TargetAppIdName += microVersion.getApp().getName() + microVersion.getName() + ",";
                        }
                    }
                }
                row.setTargetAppId(TargetAppIdName.substring(0,TargetAppIdName.length()-1));
            }



        if (rows==null){
            return ResponseEntity.ok(ResultUtil.successJson(result));
        }
        ResponseEntity<JSONObject> jsonObjectResponseEntity = ledgertypenameListAll(new SearchParams());
        Integer code = Objects.requireNonNull(jsonObjectResponseEntity.getBody()).getInteger("code");
        if (code == 100) {
            List<Map<String, Object>> data = (ArrayList<Map<String, Object>>) jsonObjectResponseEntity.getBody().get("data");
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            for (Map<String, Object> hashMap : data) {
                objectObjectHashMap.put(hashMap.get("id"),hashMap.get("name"));
            }
            for (MasterDataBatchRecords row : rows) {
                String tenantId = row.getTenantId();
                Object o = objectObjectHashMap.get(tenantId);
                row.setTenantId(String.valueOf(o));
            }


        }else {
            return ResponseEntity.ok(ResultUtil.successJson(result));
        }

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<MasterDataBatchRecords> result = masterDataBatchRecordsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody MasterDataBatchRecords entity) {
        String id = masterDataBatchRecordsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody MasterDataBatchRecords entity) {
        int rows = masterDataBatchRecordsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<MasterDataBatchRecords> entitys) {
        List<String> ids = masterDataBatchRecordsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<MasterDataBatchRecords> entitys) {
        List<String> ids = masterDataBatchRecordsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<MasterDataBatchRecords> entitys) {
        int rows = masterDataBatchRecordsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    @PostMapping(value = "ledgertypenameListAll")
    public ResponseEntity<JSONObject> ledgertypenameListAll(@RequestBody SearchParams searchParams) {
        //查看是否多次调用
        int flag = RemoteResultResolver.getRemoteFlag();
        //设置Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(RemoteResultResolver.REMOTE_FLAG, String.valueOf(flag++));
        JSONObject listAll = restTemplate.post(
                "cloud-geeke-organisation",
                "/ten/tenant/listAll",
                JSONObject.class,
                searchParams,
                headers
        );
        return ResponseEntity.ok(ResultUtil.successJson(listAll.get("data")));
    }
}
