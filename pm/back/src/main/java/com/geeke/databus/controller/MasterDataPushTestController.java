package com.geeke.databus.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.entity.MasterDataPushRecords;
import com.geeke.databus.service.DataBusBaseInterfaceService;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huihai.tang
 * @date: 2023/3/23
 */
@RestController
@RequestMapping("/master/test")
public class MasterDataPushTestController {

    @Autowired
    private DataBusBaseInterfaceService dataBusBaseInterfaceService;


    @PostMapping("/distribute")
    public ResponseEntity<JSONObject> distribute(@RequestBody MasterDataBatchRecords masterDataBatchRecords){
        MasterDataPushRecords distribute = dataBusBaseInterfaceService.distribute(masterDataBatchRecords);
        return ResponseEntity.ok(ResultUtil.successJson(distribute));
    }
}
