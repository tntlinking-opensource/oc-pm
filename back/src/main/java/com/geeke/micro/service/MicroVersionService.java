package com.geeke.micro.service;

import com.geeke.common.service.CrudService;
import com.geeke.micro.dao.MicroVersionDao;
import com.geeke.micro.entity.MicroVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用版本管理Service
 * @author
 * @version
 */

@Service("microVersionService")
@Transactional(readOnly = true)
public class MicroVersionService extends CrudService<MicroVersionDao, MicroVersion> {}
