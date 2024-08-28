package com.geeke.databus.task;

import com.geeke.common.data.Parameter;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.entity.MasterDataPushRecords;
import com.geeke.databus.service.DataBusBaseInterfaceService;
import com.geeke.databus.service.MasterDataBatchRecordsService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author TBH
 */
@Slf4j
@Component
public class DistributeDataTask {

    @Autowired
    private MasterDataBatchRecordsService masterDataBatchRecordsService;

    @Autowired
    private DataBusBaseInterfaceService dataBusBaseInterfaceService;

    public void distribute() {
        List<Parameter> parameters = Lists.newArrayList();
        parameters.add(new Parameter("push_status", "=", "0"));
        List<MasterDataBatchRecords> masterDataBatchRecords = masterDataBatchRecordsService.listAll(parameters, "");
        for (MasterDataBatchRecords masterDataBatchRecord : masterDataBatchRecords) {
            log.info("数据下发：数据为:{}", masterDataBatchRecord);
            MasterDataPushRecords distribute = dataBusBaseInterfaceService.distribute(masterDataBatchRecord);
            log.info("数据下发结果:{}", distribute);
        }
    }
}
