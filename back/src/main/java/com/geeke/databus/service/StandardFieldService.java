package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.StandardFieldDao;
import com.geeke.databus.entity.StandardField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 平台主数据Service
 * @author
 * @version
 */

@Service("standardFieldService")
@Transactional(readOnly = true)
public class StandardFieldService extends CrudService<StandardFieldDao, StandardField> {}
