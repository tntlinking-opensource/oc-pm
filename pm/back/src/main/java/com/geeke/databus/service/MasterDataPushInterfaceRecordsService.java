package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataPushInterfaceRecordsDao;
import com.geeke.databus.entity.MasterDataPushInterfaceRecords;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 主数据批次分发记录Service
 * @author
 * @version
 */

@Service("masterDataPushInterfaceRecordsService")
@Transactional(readOnly = true)
public class MasterDataPushInterfaceRecordsService
    extends CrudService<MasterDataPushInterfaceRecordsDao, MasterDataPushInterfaceRecords> {}
