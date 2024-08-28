package com.geeke.progress.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.progress.dao.ProgressReportDao;
import com.geeke.progress.entity.ProgressReport;
import com.geeke.progress.entity.ProjectProgress;
import com.geeke.projectManage.dao.ProjectListDao;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.team.entity.Project;
import com.geeke.team.service.TeamMemberService;
import com.geeke.utils.SessionUtils;
import com.geeke.wf.service.BizInProcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 进度汇报Service
 * @author
 * @version
 */

@Service("progressReportService")
@Transactional(readOnly = true)
public class ProgressReportService extends BizInProcService<ProgressReportDao, ProgressReport> {

    //进度汇报已经保存的状态码
    public static final Integer PROGRESS_SAVED=0;

    //进度汇报已经上报的状态码
    public static final Integer PROGRESS_REPORTED=1;

    @Autowired
    private ProjectListDao projectListDao;
    @Autowired
    private TeamMemberService teamMemberService;

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<ProgressReport> getBizClass() {
        return ProgressReport.class;
    }

    public List<ProjectProgress> getProjectProgress(String projectId) {
        return dao.getProjectProgress(projectId);
    }

    public List<MilestoneManage> getMilestoneByProjectId(String projectId) {
        return dao.getMilestoneByProjectId(projectId);
    }

    @Override
    public ProgressReport get(String id) {
        ProgressReport progressReport = super.get(id);
        handleMilestoneProgress(progressReport);
        return progressReport;
    }

    @Override
    public List<ProgressReport> listAll(List<Parameter> parameters, String orderby) {
        List<ProgressReport> progressReports = super.listAll(parameters, orderby);
        if (CollectionUtil.isNotEmpty(progressReports)){
            for (ProgressReport progressReport : progressReports) {
                handleMilestoneProgress(progressReport);
            }
        }
        return progressReports;
    }

    private void handleMilestoneProgress(ProgressReport progressReport) {
        String milestoneProgress = progressReport.getMilestoneProgress();
        if (StrUtil.isNotBlank(milestoneProgress)){
            List<ProjectProgress> projectProgresses = JSONObject.parseArray(milestoneProgress, ProjectProgress.class);
            List<ProjectProgress> projectProgressesDetail = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(projectProgresses)){
                for (ProjectProgress projectProgress : projectProgresses) {
                    ProjectProgress milestoneInfo = dao.getMilestoneProgressById(projectProgress.getMilestoneId());
                    if (milestoneInfo!=null){
                        projectProgress.setMilestoneName(milestoneInfo.getMilestoneName());
                        projectProgress.setProgressRate(milestoneInfo.getProgressRate());
                        projectProgressesDetail.add(projectProgress);
                    }
                }
            }
            progressReport.setProjectProgressList(projectProgressesDetail);
        }
    }

    @Override
    public ActResult beforeCreate(ProgressReport entity) {
        User userInfo = SessionUtils.getUser();
        entity.setReporter(userInfo);
        entity.setProgressStatus(PROGRESS_SAVED);
        return super.beforeCreate(entity);
    }

    @Override
    public ActResult afterAgree(ProgressReport entity) {
        entity.setProgressStatus(PROGRESS_REPORTED);
        return super.afterAgree(entity);
    }

    @Override
    public ProgressReport getByTaskId(String taskId) {
        ProgressReport report = super.getByTaskId(taskId);
        handleMilestoneProgress(report);
        return report;
    }

    @Override
    public Page<ProgressReport> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        Page<ProgressReport> progressReportPage = super.listPage(parameters, offset, limit, orderby);
        List<ProgressReport> rows = progressReportPage.getRows();
        if (CollectionUtil.isNotEmpty(rows)){
            for (ProgressReport row : rows) {
                handleMilestoneProgress(row);
            }
        }
        return progressReportPage;
    }

    public List<ProjectList> listProjectAll(List<Parameter> params, String orderby) {
        JSONObject userInfo = com.geeke.sys.utils.SessionUtils.getUserJson();
        // TODO 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if (!teamMemberService.checkIsAdmin(userId)){
            params.add(new Parameter("project_manager", "=", userId));
        }
        params.add(new Parameter("project_status", "in", Arrays.asList("2","3","4")));
        PageRequest pageRequest = new PageRequest(params, orderby);
        return projectListDao.listAll(pageRequest);

    }
}
