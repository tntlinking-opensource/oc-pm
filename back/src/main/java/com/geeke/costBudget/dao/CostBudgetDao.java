package com.geeke.costBudget.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.costBudget.entity.CostBudget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成本预算DAO接口
 * @author
 * @version
 */
@Mapper
public interface CostBudgetDao extends CrudDao<CostBudget> {

    List<CostBudget> getByProjectId(@Param("projectId") String projectId);

}
