package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.databus.entity.ProductionRelation;
import com.geeke.databus.service.AppMasterDataTypeService;
import com.geeke.databus.service.ProductionRelationService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.RemoteResultResolver;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
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
 * 生产消费关系Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/databus/productionRelation")
public class ProductionRelationController extends BaseController {

    @Autowired
    private ProductionRelationService productionRelationService;
    @Autowired
    private AppMasterDataTypeService appMasterDataTypeService;
    @Autowired
    private DynamicRestTemplate restTemplate;
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ProductionRelation entity = productionRelationService.get(id);
        ProductionRelation entity1 = productionRelationService.getByMasterIdAndtenantId(entity.getTenantId(),entity.getMasterDataTypeId());
        return ResponseEntity.ok(ResultUtil.successJson(entity1));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<ProductionRelation> result = productionRelationService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        List<ProductionRelation> rows = result.getRows();
        ResponseEntity<JSONObject> jsonObjectResponseEntity = ledgertypenameListAll(new SearchParams());
        Integer code = Objects.requireNonNull(jsonObjectResponseEntity.getBody()).getInteger("code");

        if (code == 100) {
            List<Map<String, Object>> data = (ArrayList<Map<String, Object>>) jsonObjectResponseEntity.getBody().get("data");
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            for (Map<String, Object> hashMap : data) {
                objectObjectHashMap.put(hashMap.get("id"),hashMap.get("name"));
            }
            for (ProductionRelation row : rows) {
                String tenantId = row.getTenantId();
                Object o = objectObjectHashMap.get(tenantId);
                row.setTenantName(String.valueOf(o));
            }
        }else {
            return ResponseEntity.ok(ResultUtil.successJson(result));
        }
//        ArrayList<ProductionRelation> objects = new ArrayList<>();
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//        int i = 0;
//        for (ProductionRelation row : rows) {
//            String tenantId = row.getTenantId();
//            String masterDataTypeId = row.getMasterDataTypeId();
//            if (objectObjectHashMap.containsKey(tenantId+masterDataTypeId)){
//                ProductionRelation productionRelation = objects.get((Integer) objectObjectHashMap.get(tenantId + masterDataTypeId));
//                if (row.getDataModel().equals("1")) {
//                    row.setDataModel("");
//                    if (productionRelation.getAppId().equals("")){
//                        productionRelation.setAppId(productionRelation.getAppId()+row.getAppId());
//                    }else {
//                        productionRelation.setAppId(productionRelation.getAppId()+"、"+row.getAppId());
//                    }
//
//                }else {
//                    row.setDataModel("");
//                    if (productionRelation.getDataModel().equals("")){
//                        productionRelation.setDataModel(productionRelation.getDataModel()+row.getAppId());
//                    }else {
//                        productionRelation.setDataModel(productionRelation.getDataModel()+"、"+row.getAppId());
//                    }
//
//                }
//            }else {
//                objectObjectHashMap.put(tenantId+masterDataTypeId,i);
//                i++;
//                ProductionRelation productionRelation = row;
//                if (row.getDataModel().equals("1")) {
//                    row.setDataModel("");
//                    productionRelation.setAppId(productionRelation.getAppId());
//                }else {
//                    row.setDataModel("");
//                    productionRelation.setDataModel(productionRelation.getAppId());
//                }
//                objects.add(productionRelation);
//            }
//
//        }
//        result=new Page<>(objects.size(),objects);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<ProductionRelation> result = productionRelationService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody ProductionRelation  entity) {
        try {
            productionRelationService.saveall(entity);
            return ResponseEntity.ok(ResultUtil.successJson());
        }catch (Exception e) {
//            throw new RuntimeException(e);
        return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
    }

    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ProductionRelation entity) {
        int rows = productionRelationService.deleteByMasterIdAndtenantId(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<ProductionRelation> entitys) {
        List<String> ids = productionRelationService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<ProductionRelation> entitys) {
        List<String> ids = productionRelationService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<ProductionRelation> entitys) {
        int rows = productionRelationService.bulkDelete(entitys);
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
