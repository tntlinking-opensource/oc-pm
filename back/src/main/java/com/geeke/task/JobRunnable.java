package com.geeke.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.geeke.schedule.common.Constants;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.schedule.service.ScheduleJobService;
import com.geeke.utils.SpringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobRunnable implements Runnable {

	ScheduleJob job;

    ScheduleJobService scheduleJobService;

    String tenantId;
	
	JobRunnable(ScheduleJob job, ScheduleJobService scheduleJobService) {
		this.job = job;
        this.scheduleJobService = scheduleJobService;

        String tenantId = DynamicDataSourceContextHolder.peek();
        this.tenantId = tenantId == null ? "0" : tenantId;
	}
	
	@Override
	public void run() {
		try {
            /* 多租户多数据源处理，切换任务所在的租户对应的数据库 */
            // 清空当前线程的数据源信息
    	    DynamicDataSourceContextHolder.clear(); 
    	    // 切换到对应poolName的数据源
    	    DynamicDataSourceContextHolder.push(tenantId);

            /*
             * 启动多个服务，如集群模式模式下，
             * 通过其中一个服务删除、禁用、停用定时任务后，另外服务的定时任务也应该终止
             */
            ScheduleJob newJob = scheduleJobService.get(job.getId());
            if(Constants.TaskRunStatus.STOPPED.getCode().equals(newJob.getStatus())
                || Constants.TaskLockStatus.LOCKED.getCode().equals(newJob.getIsLock())
                || !"0".equals(newJob.getDelFlag())) {
                ScheduleUtil.cancel(job.getId());
                return;
            }

            Class<?> proxyClass = Class.forName(job.getJavaClass());
            Object beanObject = SpringUtils.getBean(proxyClass);
            if (StringUtils.isBlank(job.getParams())) {
                Method method = proxyClass.getMethod(job.getJavaMethod());
                method.invoke(beanObject);
            } else {
                String[] group = job.getParams().split(":");
                if ("String".equals(group[0])) {
                    Method method = proxyClass.getMethod(job.getJavaMethod(), String.class);
                    method.invoke(beanObject, String.valueOf(group[1]));
                }
                if ("Boolean".equals(group[0])) {
                    Method method = proxyClass.getMethod(job.getJavaMethod(), Boolean.class);
                    method.invoke(beanObject, Boolean.valueOf(group[1]));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
        	e.printStackTrace();
            String errorMessage = null;
            if (e instanceof InvocationTargetException) {
                StackTraceElement stackTraceElement = ((InvocationTargetException) e).getTargetException().getStackTrace()[0];
                errorMessage = String.format("由%s#%s(%s)触发。业务代码报错信息：类型路径[%s]，方法[%s]，错误行号[%s]。",
                        job.getJavaClass(), job.getJavaMethod(), (StringUtils.isNotBlank(job.getParams()) ? job.getParams() : ""),
                        stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()
                );
            }
            throw new RuntimeException("定时任务运行异常！异常信息：" + (StringUtils.isNotBlank(errorMessage) ? errorMessage : e.getMessage()));
        }		
	}

}
