package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataJsonDao;
import com.geeke.databus.entity.MasterDataJson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 主数据批次记录Service
 * @author
 * @version
 */

@Service("masterDataJsonService")
@Transactional(readOnly = true)
public class MasterDataJsonService extends CrudService<MasterDataJsonDao, MasterDataJson> {}
