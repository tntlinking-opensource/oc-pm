package com.geeke.progress.dao;

import com.geeke.progress.entity.ProgressReport;
import com.geeke.progress.entity.ProjectProgress;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进度汇报DAO接口
 * @author
 * @version
 */
@Mapper
public interface ProgressReportDao extends WfCrudDao<ProgressReport> {
    List<ProjectProgress> getProjectProgress(String projectId);
    ProjectProgress getMilestoneProgressById(String milestoneId);

    List<MilestoneManage> getMilestoneByProjectId(String projectId);
}
