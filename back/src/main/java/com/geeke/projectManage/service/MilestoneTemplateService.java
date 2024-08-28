package com.geeke.projectManage.service;

import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.BaseEntity;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.dao.MilestoneTemplateDao;
import com.geeke.projectManage.entity.MilestoneTemplate;
import com.geeke.projectManage.entity.MilestoneTemplateItem;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 里程碑模板Service
 * @author
 * @version
 */

@Service("milestoneTemplateService")
@Transactional(readOnly = true)
public class MilestoneTemplateService extends CrudService<MilestoneTemplateDao, MilestoneTemplate> {

    @Autowired
    private MilestoneTemplateItemService milestoneTemplateItemService;

    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<MilestoneTemplate> entitys) {
        int rows = bulkDelete(entitys);
        List<String> ids = entitys.stream().map(BaseEntity::getId).collect(Collectors.toList());
        Parameter parameter = new Parameter("template_id", "in", ids);
        List<Parameter> parameters = Collections.singletonList(parameter);
        List<MilestoneTemplateItem> items = milestoneTemplateItemService.listAll(parameters, "id");
        // 删除模板下的详情信息
        milestoneTemplateItemService.bulkDelete(items);
        return rows;
    }
}
