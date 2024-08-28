package com.geeke.projectManage.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.UserRole;
import com.geeke.admin.service.UserRoleService;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.projectManage.common.PageRequestExp;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.dao.ProjectListDao;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.ReportManage;
import com.geeke.projectManage.entity.StatusConstant;
import com.geeke.projectManage.entity.vo.RateVO;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.service.DictItemService;
import com.geeke.sys.service.SysFileService;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.IdGen;
import com.geeke.utils.StringUtils;
import com.geeke.wf.service.BizInProcService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 项目清单Service
 * @author
 * @version
 */

@Service("projectListService")
@Transactional(readOnly = true)
public class ProjectListService extends BizInProcService<ProjectListDao, ProjectList> {

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private ProjectListDao projectListDao;

    @Autowired
    private MilestoneManageService milestoneManageService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SysFileService sysFileService;

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<ProjectList> getBizClass() {
        return ProjectList.class;
    }

    @Override
    public ActResult beforeCreate(ProjectList entity) {
        // 项目审批发起流程，更新业务表项目状态为审批中
        String dictItemId = "1";
        DictItem dictItem = dictItemService.get(dictItemId);
        entity.setProjectStatus(dictItem);
        return super.beforeCreate(entity);
    }

    @Override
    public ActResult afterReapply(ProjectList entity) {
        // 重新申请，修改项目状态为审批中
        String dictItemId = "1";
        handleEntityProjectStatus(dictItemId, entity);
        return super.afterReapply(entity);
    }

    @Override
    public ActResult afterBack(ProjectList entity, boolean toStart) {
        // 项目审批回退至初始节点，更新业务表项目状态为审批退回
        if (toStart) {
            String dictItemId = "9";
            handleEntityProjectStatus(dictItemId, entity);
        }
        return super.afterBack(entity, toStart);
    }

    @Override
    public ActResult afterAgree(ProjectList entity) {
        // 审批结束，修改项目状态为审批通过
        if (entity.isApproved()) {
            String dictItemId = "2";
            handleEntityProjectStatus(dictItemId, entity);
        }
        return super.afterAgree(entity);
    }

    /**
     * 修改项目状态
     * @param dictItemId    字典值ID
     * @param entity        项目清单数据
     * @author Yogaa
     * @date 11:08 2023/12/9
     */
    public void handleEntityProjectStatus(String dictItemId, ProjectList entity) {
        DictItem dictItem = dictItemService.get(dictItemId);
        entity.setProjectStatus(dictItem);
        projectListDao.updateProjectStatusById(entity);
    }

    public List<ProjectList> listAllWithMilestone(PageRequestExp<String> pageReq) {
        List<ProjectList> projects = listAll(pageReq);
        // 查询项目下的里程碑数据
        String projectIds = projects.stream().map(ProjectList::getId).collect(Collectors.joining(","));
        PageRequestExp<String> pr = new PageRequestExp<>(null, projectIds);
        List<MilestoneManage> milestones = milestoneManageService.listAll(pr);
        // 处理项目下的里程碑信息
        projects.forEach(project -> {
            String projectId = project.getId();
            List<MilestoneManage> projectMilestones = milestones.stream().filter(milestone -> {
                ProjectList p = milestone.getProject();
                return !Objects.isNull(p) && Objects.equals(projectId, p.getId());
            }).collect(Collectors.toList());
            project.setMilestones(projectMilestones);
        });
        return projects;
    }

    public Page<ProjectList> listPage(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if (!checkIsAdmin(userId)) {
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return listPage(pr);
    }

    public List<ProjectList> listAll(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if (!checkIsAdmin(userId)) {
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return listAll(pr);
    }

    public List<ProjectList> listByTeamMember(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // admin可以查看所有项目
        String userId = userInfo.getString("id");
        if (!checkIsAdmin(userId)) {
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return projectListDao.listByTeamMember(pr);
    }

    /**
     * 判断当前用户是否有admin角色
     * @param userId    用户ID
     * @author Yogaa
     * @date 11:48 2024/1/13
     */
    public boolean checkIsAdmin(String userId) {
        String adminRoleId = "1861953878566559890";
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("user_id", "=", userId));
        List<UserRole> userRoles = userRoleService.listAll(params, null);
        return userRoles.stream().anyMatch(item -> adminRoleId.equals(item.getRole().getId()));
    }

    public boolean checkIsAdmin() {
        JSONObject userInfo = SessionUtils.getUserJson();
        String userId = userInfo.getString("id");
        return checkIsAdmin(userId);
    }

    public boolean checkCompleted(String id) {
        // 查询项目下的里程碑信息
        PageRequestExp<String> pr = new PageRequestExp<>(null, id);
        List<MilestoneManage> milestones = milestoneManageService.listAll(pr);
        // TODO 项目下没有里程碑，验证不通过
        if (milestones.isEmpty()) {
            return false;
        }
        // 判断里程碑的状态是否全为已完成
        Optional<MilestoneManage> opt = milestones.stream().filter(item -> !StatusConstant.MILESTONE_STATUS_COMPLETED.equals(item.getMilestoneStatus().getId())).findAny();
        return !opt.isPresent();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean saveProjectCompleted(ProjectList projectList, MultipartFile[] annexUploads, String[] deleteIds)
        throws java.io.IOException {
        if (StringUtils.isEmpty(projectList.getCompleteAnnex())) {
            projectList.setCompleteAnnex(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(annexUploads, projectList.getCompleteAnnex());
        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }
        projectList.preUpdate();
        return projectListDao.saveProjectCompleted(projectList);
    }

    public List<ProjectList> listMap(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if (!checkIsAdmin(userId)) {
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return projectListDao.listMap(pr);
    }
}
