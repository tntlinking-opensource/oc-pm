package com.geeke.projectManage.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.projectManage.entity.ReportManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 周报管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface ReportManageDao extends CrudDao<ReportManage> {

    List<ReportManage> getListByProjectIdAndDate(@Param("projectId") String projectId, @Param("reportDate") String reportDate);

}
