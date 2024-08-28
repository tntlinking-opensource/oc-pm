package com.geeke.team.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.team.entity.Project;
import com.geeke.team.entity.TeamMember;
import com.geeke.team.entity.TeamRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 团队管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TeamMemberDao extends CrudDao<TeamMember> {
    List<Project> projectListPage(PageRequest pageRequest);
    Project projectById(String id);

    int projectCount(PageRequest pageRequest);

    List<TeamRole> listTeamStatRole(String id);

    List<TeamRole> listTeamRole();

}
