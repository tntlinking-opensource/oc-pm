package com.geeke.dbtype.service;

import com.geeke.common.service.CrudService;
import com.geeke.dbtype.dao.SysDbTypeDao;
import com.geeke.dbtype.entity.SysDbType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库类型管理Service
 * @author
 * @version
 */

@Service("sysDbTypeService")
@Transactional(readOnly = true)
public class SysDbTypeService extends CrudService<SysDbTypeDao, SysDbType> {}
