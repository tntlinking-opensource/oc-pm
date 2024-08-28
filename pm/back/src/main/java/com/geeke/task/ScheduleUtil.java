package com.geeke.task;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.geeke.common.data.Parameter;
import com.geeke.schedule.common.Constants;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.schedule.service.ScheduleJobService;
import com.geeke.utils.AppVersionUtils;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 利用线程池实现任务调度
 * Task任务调度器可以实现任务的调度和删除
 * 原理:
 * 实现一个类：ThreadPoolTaskScheduler线程池任务调度器，能够开启线程池进行任务调度
 * ThreadPoolTaskScheduler.schedule（）方法会创建一个定时计划ScheduleFuture,
 * 在这个方法中添加两个参数一个是Runable:线程接口类，和CronTrigger(定时任务触发器)
 * 在ScheduleFuture中有一个cancel可以停止定时任务
 * @Author: shenzy
 * @DateTime: 2021/10/28 10:51
 * @Description: TODO
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduleUtil implements CommandLineRunner {
    @Autowired
    AppVersionUtils appVersionUtils;
    
	@Autowired
	ScheduleJobService scheduleJobService;

    private static ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    static {
        threadPoolTaskScheduler.initialize();
        log.info("定时任务启动");
    }

    /**
     * 定时任务的启动
     * @param runnable  执行的任务
     * @param trigger   表达式
     * @param code      编码
     */
    public synchronized static void start(Runnable runnable, Trigger trigger, String code) {
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(runnable, trigger);
        JobMapAdapter.put(code, scheduledFuture);
        log.info("启动定时任务" + code);
    }

    /**
     * 定时任务关闭
     * @param code
     */
    public synchronized static void cancel(String code) {
        ScheduledFuture<?> scheduledFuture = JobMapAdapter.get(code);
        if (scheduledFuture != null/* && scheduledFuture.isCancelled()*/) {
            scheduledFuture.cancel(true);
            JobMapAdapter.remove(code);
        }
        log.info("取消定时任务" + code);
    }

    /**
     * 定时任务重启
     * @param runnable
     * @param trigger
     * @param code
     */
    public synchronized static void reset(Runnable runnable, Trigger trigger, String code) {
        ScheduledFuture<?> scheduledFuture = JobMapAdapter.get(code);
        if (scheduledFuture != null && scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        JobMapAdapter.remove(code);

        scheduledFuture = threadPoolTaskScheduler.schedule(runnable, trigger);
        JobMapAdapter.put(code, scheduledFuture);
        log.info("修改定时任务" + code);
    }

	@Override
	public void run(String... args) throws Exception {
		List<Parameter> parameters = Lists.newArrayList();
		parameters.add(new Parameter("status", "=", Constants.TaskRunStatus.RUNNING.getCode()));
        parameters.add(new Parameter("is_lock", "=", Constants.TaskLockStatus.UNLOCKED.getCode()));
		
        // 启动多数据源的主库任务
        List<ScheduleJob> jobs = scheduleJobService.listAll(parameters, null);
		for(ScheduleJob job: jobs) {
	        Runnable runnable = new JobRunnable(job, scheduleJobService);
	        ScheduleUtil.start(runnable, new CronTrigger(job.getCron()), job.getId());
		}

        // 启动租户应用数据库任务
        JSONArray tenantApps = appVersionUtils.getCurrentTenatApps();
        for(int i = 0; i < tenantApps.size(); i++) {
            String tenantId = tenantApps.getJSONObject(i).getJSONObject("tenant").getString("id");
			// 清空当前线程的数据源信息
    	    DynamicDataSourceContextHolder.clear(); 
    	    // 切换到对应poolName的数据源
    	    DynamicDataSourceContextHolder.push(tenantId);

            List<ScheduleJob> tenantJobs = scheduleJobService.listAll(parameters, null);
            for(ScheduleJob job: tenantJobs) {
                Runnable runnable = new JobRunnable(job, scheduleJobService);
                ScheduleUtil.start(runnable, new CronTrigger(job.getCron()), job.getId());
            }
        }
	}

}

