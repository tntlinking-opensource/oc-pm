package com.geeke.wf.candidate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.VariableScope;
import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.candidate.ICandidate;
import com.geeke.camundaex.constant.WfConstant;
import com.geeke.camundaex.entity.ActUser;
import com.geeke.org.dao.DepartmentDao;
import com.geeke.org.entity.Department;
import com.geeke.utils.SpringUtils;
import com.google.common.collect.Lists;

/**
 * 获取发起人所属部门的经理作为审批后续人
 * @author 
 *
 */
public class StarterDepartManager extends SpringUtils implements ICandidate {

	@Override
	public List<ActUser> execute(VariableScope variableScope, String param) {
		List<ActUser> actUsers = Lists.newArrayList();
		
		String starterId = (String)variableScope.getVariable(WfConstant.START_UP_USER_ID);
		UserDao userDao = (UserDao)getBean("userDao");
        User starter = userDao.get(starterId);
        if(starter != null && starter.getDepartment() != null && StringUtils.isNotBlank(starter.getDepartment().getId())) {
        	DepartmentDao departmentDao = (DepartmentDao)getBean("departmentDao");
        	Department department = departmentDao.get(starter.getDepartment().getId());
        	if(department != null & department.getManager() != null && StringUtils.isNotBlank(department.getManager().getId()) ) {
        		actUsers.add(new ActUser(department.getManager().getId(), department.getManager().getName()));
        	}
        }
		
		return actUsers;
	}
}
