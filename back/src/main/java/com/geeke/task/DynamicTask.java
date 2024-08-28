package com.geeke.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.schedule.common.Constants;
import com.geeke.schedule.dao.ScheduleJobDao;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.schedule.service.ScheduleJobService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: shenzy
 * @DateTime: 2021/10/22 16:42
 * @Description: TODO
 */
@Api(value = "/schedule/dynamicTask", tags = {"定时任务Controller"})
@RestController
@RequestMapping(value = "/schedule/dynamicTask")
@Slf4j
public class DynamicTask {

    @Autowired
    private ScheduleJobService scheduleJobService;
    
    /**
     * 启动定时器
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "启动定时器", notes = "启动定时器", httpMethod = "POST",response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response =  String.class)
    })
    @PostMapping(value = "startTask")
    public ResponseEntity<JSONObject> startTask(@RequestBody ScheduleJob scheduleJob) {
        ScheduleJob job = scheduleJobService.get(scheduleJob.getId());
        if (job.getIsLock().equals(Constants.TaskLockStatus.LOCKED.getCode())) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "该任务已禁用，不可启动"));
        }
        Runnable runnable = new JobRunnable(job, scheduleJobService);
        ScheduleUtil.start(runnable, new CronTrigger(job.getCron()), job.getId());
        // 变更 任务状态：运行中
        job.setStatus(Constants.TaskRunStatus.RUNNING.getCode());
        // 更新任务
        scheduleJobService.save(job);
        return ResponseEntity.ok(ResultUtil.successJson(String.format("编号：%s，任务名称：%s，启动成功。", job.getId(), job.getName())));
    }

    /**
     * 停止定时任务
     * 定义一个方法实现用于终止定时任务
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "停止定时任务", notes = "停止定时任务 定义一个方法实现用于终止定时任务", httpMethod = "POST",response = String.class)
    @PostMapping(value = "endTask")
    public ResponseEntity<JSONObject> endTask(@RequestBody ScheduleJob scheduleJob) {
        ScheduleJob job = scheduleJobService.get(scheduleJob.getId());
        // 取消任务
        ScheduleUtil.cancel(job.getId());
        // 已暂停
        job.setStatus(Constants.TaskRunStatus.STOPPED.getCode());
        // 更新任务
        scheduleJobService.save(job);
        return ResponseEntity.ok(ResultUtil.successJson(job.getId() + " : 任务，取消成功"));
    }


    /**
     * 修改定时任务时间：changeCron
     * 改变调度的时间
     * 步骤：
     * 1,先停止定时器
     * 2,在启动定时器
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "修改定时任务时间", notes = "修改定时任务时间：changeCron 改变调度的时间 步骤： 1,先停止定时器 2,在启动定时器", httpMethod = "POST",response = String.class)
    @PostMapping(value = "restartTask")
    public ResponseEntity<JSONObject> restartTask(@RequestBody ScheduleJob scheduleJob) {
        // 停止定时器
        endTask(scheduleJob);
        // 开启
        startTask(scheduleJob);
        return ResponseEntity.ok(ResultUtil.successJson(scheduleJob.getId() + " : 任务，重启成功"));
    }

}
