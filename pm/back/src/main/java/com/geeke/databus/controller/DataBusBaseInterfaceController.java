package com.geeke.databus.controller;


import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Parameter;
import com.geeke.databus.entity.AppMasterDataType;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.entity.MasterDataDetail;
import com.geeke.databus.entity.MasterDataPushRecords;
import com.geeke.databus.entity.ProductionRelation;
import com.geeke.databus.service.AppMasterDataTypeService;
import com.geeke.databus.service.DataBusBaseInterfaceService;
import com.geeke.databus.service.ProductionRelationService;
import com.geeke.databus.service.UniversalInitializationService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * databus 通用数据接口
 */
@RestController
@RequestMapping(value = "/databus/dataBusBaseInterface")
@RequiredArgsConstructor
public class DataBusBaseInterfaceController {

    private static final String TENANT_ID = "tenant_id";
    private static final String APP_ID = "app_id";
    private static final String DATA_TYPE_CODE = "data_type_code";
    private static final String DATA_MODEL = "data_model";
    private final DataBusBaseInterfaceService dataBusBaseInterfaceService;
    private final AppMasterDataTypeService appMasterDataTypeService;
    private final ProductionRelationService productionRelationService;
    private final UniversalInitializationService universalInitializationService;

    @PostMapping("/receive")
    public ResponseEntity<JSONObject> receive(@RequestBody JSONObject jsonObject) {
        //例子--数字类：113233
        String tenantId = jsonObject.getString(TENANT_ID);
        //例子--数字类：213213213
        String sourceAppId = jsonObject.getString(APP_ID);
        //例子--code:Company
        String dataTypeCode = jsonObject.getString(DATA_TYPE_CODE);
        //例子--name:公司
        String dataTypeName = jsonObject.getString("data_type_name");
        JSONObject result = dataBusBaseInterfaceService.receive(tenantId, sourceAppId,dataTypeCode, jsonObject);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/initdata")
    public ResponseEntity<JSONObject> initdata(@RequestBody JSONObject jsonObject) {
        //传过来应用+租户名称然后数据库查找数据   然后返回数据
        String tenantId = jsonObject.getString(TENANT_ID);
        //source_app
        String sourceAppId = jsonObject.getString(APP_ID);

        List<Parameter> params2 = Lists.newArrayList();
        params2.add(new Parameter(TENANT_ID, "=", sourceAppId));
        params2.add(new Parameter("consumer_work_model", "=", 1));

        List<AppMasterDataType> appMasterDataTypes = appMasterDataTypeService.listAll(params2,"a.update_date");
        //查询 过来的应用是否已经审核并且有权限 如果没有的话 直接返回提示信息
        if (appMasterDataTypes.size()==0){
            return ResponseEntity.ok(ResultUtil.warningJson(ErrorEnum.E_50002, "当前应用没有初始化权限！请配置应用主数据"));
        }

        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter(DATA_MODEL, "=", 0));
        params.add(new Parameter(APP_ID, "=", sourceAppId));
        List<ProductionRelation> update_date = productionRelationService.listAll(params, "a.update_date");
        if (update_date.size()==0){
            return ResponseEntity.ok(ResultUtil.warningJson(ErrorEnum.E_50002, "当前租户没有初始化权限！配置生产消费关系"));
        }
        List<MasterDataDetail> initData = universalInitializationService.getInitData(tenantId, sourceAppId);
        return ResponseEntity.ok(ResultUtil.successJson(initData));
    }

    @PostMapping("/test")
    public ResponseEntity<JSONObject> test() {
        return ResponseEntity.ok(ResultUtil.successJson(universalInitializationService.parsingJsonData()));
    }

    @PostMapping("/distribute")
    public ResponseEntity<JSONObject> distribute(MasterDataBatchRecords masterDataBatchRecords){
        MasterDataPushRecords records = dataBusBaseInterfaceService.distribute(masterDataBatchRecords);
        return ResponseEntity.ok(ResultUtil.successJson(records));
    }
}
