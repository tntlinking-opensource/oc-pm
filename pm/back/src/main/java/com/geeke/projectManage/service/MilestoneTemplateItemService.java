package com.geeke.projectManage.service;

import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.dao.MilestoneTemplateItemDao;
import com.geeke.projectManage.entity.MilestoneTemplateItem;
import com.geeke.projectManage.entity.vo.RateVO;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 里程碑模板详情Service
 * @author
 * @version
 */

@Service("milestoneTemplateItemService")
@Transactional(readOnly = true)
public class MilestoneTemplateItemService extends CrudService<MilestoneTemplateItemDao, MilestoneTemplateItem> {

    public boolean checkProgressRate(RateVO vo) {
        Parameter parameter = new Parameter("template_id", "=", vo.getId());
        List<Parameter> parameters = Collections.singletonList(parameter);
        List<MilestoneTemplateItem> items = listAll(parameters, null);
        // 将模板下的详情的进度值相加
        // 编辑的时候需要排除自身
        int sum = items.stream().filter(item -> !Objects.equals(item.getId(), vo.getExcludeId())).mapToInt(MilestoneTemplateItem::getProgressRate).sum();
        return sum <= 100 - vo.getRate();
    }
}
