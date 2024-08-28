package com.geeke.projectManage.dao;

import com.geeke.projectManage.common.PageRequestExp;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目清单DAO接口
 * @author
 * @version
 */
@Mapper
public interface ProjectListDao extends WfCrudDao<ProjectList> {

    /**
     * 通过项目ID修改项目状态
     * @param entity    项目数据
     * @author Yogaa
     * @date 1:17 2023/12/9
     */
    boolean updateProjectStatusById(ProjectList entity);

    /**
     * 项目完结
     * @author Yogaa
     * @date 11:03 2024/1/4
     */
    boolean saveProjectCompleted(ProjectList projectList);

    /**
     * 通过团队成员来查询权限范围内的项目
     * @author Yogaa
     * @date 22:21 2024/1/16
     */
    List<ProjectList> listByTeamMember(PageRequestExp<String> pr);

    /**
     * 查询审批通过的项目 *
     * @param pageRequest
     * @return
     */
    List<ProjectList> listMap(PageRequestExp pageRequest);
}
