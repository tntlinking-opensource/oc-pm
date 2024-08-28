package com.geeke.requirement.service;

import cn.hutool.core.collection.CollectionUtil;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.common.service.CrudService;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.requirement.dao.RequirementManageDao;
import com.geeke.requirement.entity.RequirementManage;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 需求管理Service
 * @author
 * @version
 */

@Service("requirementManageService")
@Transactional(readOnly = true)
public class RequirementManageService extends CrudService<RequirementManageDao, RequirementManage> {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SysFileService sysFileService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public RequirementManage save(RequirementManage requirementManage) {
        // 新增时, 处理自动编号字段
        if (StringUtils.isBlank(requirementManage.getId())) {
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "requirement_code", requirementManage);
            requirementManage.setRequirementCode(sn);
        }

        RequirementManage requirementManageTemp = super.save(requirementManage);

        return requirementManageTemp;
    }

    @Override
    public Page<RequirementManage> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        Page<RequirementManage> requirementManagePage = super.listPage(parameters, offset, limit, orderby);
        List<RequirementManage> pageRows = requirementManagePage.getRows();
        if (CollectionUtil.isNotEmpty(pageRows)){
            for (RequirementManage pageRow : pageRows) {
                List<Parameter> params = Lists.newArrayList();
                params.add(new Parameter("object_id", "=", pageRow.getId()));
                pageRow.setAttachmentFile(sysFileService.listAll(params,null));
            }
        }
        return requirementManagePage;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public RequirementManage save(RequirementManage entity, String[] deleteIds, MultipartFile[] attachments) throws IOException {

        RequirementManage save = save(entity);

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }
        // 保存附件
        if (attachments!=null && attachments.length>0){
            sysFileService.changeAndSaveSysFileList(attachments,save.getId());
        }
        return save;
    }

    public List<MilestoneManage> listMilestoneAll(List<Parameter> params, String orderby) {
        PageRequest pageRequest = new PageRequest(params, orderby);
        return dao.listMilestoneAll(pageRequest);
    }

    public List<ScheduleManage> listScheduleAll(List<Parameter> params, String orderby) {
        PageRequest pageRequest = new PageRequest(params, orderby);
        return dao.listScheduleAll(pageRequest);
    }
}
