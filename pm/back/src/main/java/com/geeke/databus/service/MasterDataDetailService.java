package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataDetailDao;
import com.geeke.databus.entity.MasterDataDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 主数据解析数据Service
 * @author
 * @version
 */

@Service("masterDataDetailService")
@Transactional(readOnly = true)
public class MasterDataDetailService extends CrudService<MasterDataDetailDao, MasterDataDetail> {}
