package com.geeke.projectManage.service;

import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.common.PageRequestExp;
import com.geeke.projectManage.dao.MilestoneManageDao;
import com.geeke.projectManage.entity.*;
import com.geeke.projectManage.entity.vo.RateVO;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 里程碑管理Service
 * @author
 * @version
 */

@Service("milestoneManageService")
@Transactional(readOnly = true)
public class MilestoneManageService extends CrudService<MilestoneManageDao, MilestoneManage> {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    @Lazy
    private ProjectListService projectListService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public MilestoneManage save(MilestoneManage milestoneManage, MultipartFile[] annexUploads, String[] deleteIds)
        throws java.io.IOException {
        if (StringUtils.isEmpty(milestoneManage.getAnnex())) {
            milestoneManage.setAnnex(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(annexUploads, milestoneManage.getAnnex());

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }

        MilestoneManage milestoneManageTemp = super.save(milestoneManage);

        return milestoneManageTemp;
    }

    /**
     * 修改里程碑状态
     * @param milestoneId   里程碑ID
     * @param milestoneStatus 里程碑状态
     * @author Yogaa
     * @date 0:42 2023/12/29
     */
    public boolean handleMilestoneStatus(String milestoneId, String milestoneStatus) {
        // 当里程碑状态为进行中时，更新里程碑实际开始日期；当里程碑状态为已完成时，更新里程碑实际结束日期
        String status = StatusConstant.MILESTONE_STATUS_PROGRESS.equals(milestoneStatus) ? "1" : StatusConstant.MILESTONE_STATUS_COMPLETED.equals(milestoneStatus) ? "2" : null;
        boolean flag = dao.handleMilestoneStatus(milestoneId, milestoneStatus, status);
        MilestoneManage milestone = get(milestoneId);
        handleProjectStatus(milestone.getProject().getId());
        return flag;
    }

    /**
     * 判断项目下的里程碑状态，修改项目状态
     * @param projectId   项目ID
     * @author Yogaa
     * @date 22:21 2024/1/4
     */
    public void handleProjectStatus(String projectId) {
        List<MilestoneManage> milestones = getMilestoneByProjectId(projectId);
        // 判断当前项目下的里程碑状态来修改项目状态
        Set<String> statusList = milestones.stream().map(ms -> ms.getMilestoneStatus().getId()).collect(Collectors.toSet());
        // 项目下里程碑状态MILESTONE_STATUS中存在进行中时，修改项目状态为进行中
        if (statusList.contains(StatusConstant.MILESTONE_STATUS_PROGRESS)) {
            String dictItemId = "3";
            ProjectList project = new ProjectList();
            project.setId(projectId);
            projectListService.handleEntityProjectStatus(dictItemId, project);
        }
    }

    public List<MilestoneManage> getMilestoneByProjectId(String projectId) {
        Parameter parameter = new Parameter("project_id", "=", projectId);
        List<Parameter> parameters = Collections.singletonList(parameter);
        PageRequestExp<String> pre = new PageRequestExp<>(parameters, null);
        return listAll(pre);
    }

    public boolean checkProgressRate(RateVO vo) {
        Parameter parameter = new Parameter("project_id", "=", vo.getId());
        List<Parameter> parameters = Collections.singletonList(parameter);
        PageRequestExp<String> pre = new PageRequestExp<>(parameters, null);
        List<MilestoneManage> items = listAll(pre);
        // 将模板下的详情的进度值相加
        int sum = items.stream().filter(item -> !Objects.equals(item.getId(), vo.getExcludeId())).mapToInt(MilestoneManage::getProgressRate).sum();
        return sum <= 100 - vo.getRate();
    }
}
