package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataRecordsDao;
import com.geeke.databus.entity.MasterDataRecords;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 主数据批次记录Service
 * @author
 * @version
 */

@Service("masterDataRecordsService")
@Transactional(readOnly = true)
public class MasterDataRecordsService extends CrudService<MasterDataRecordsDao, MasterDataRecords> {}
