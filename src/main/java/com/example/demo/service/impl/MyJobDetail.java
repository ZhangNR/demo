package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * JobDetail
 * 实现Job接口，重写execute()方法，方法内容就是具体的业务逻辑。
 * 如果是动态任务呢，比如取消订单，每次执行都是不同的订单号。这个时候就需要在创建任务(JobDetail)或者创建触发器(Trigger)那里传入参数，然后在这里通过JobExecutionContext来获取参数进行处理。
 *
 * @author ZhangJP
 * @date 2020/11/3
 */
@Slf4j
public class MyJobDetail implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("定时任务 detail-> jobDataMap -> name: {}",jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
        log.info("定时任务 detail-> jobDataMap -> age: {}",jobExecutionContext.getJobDetail().getJobDataMap().get("age"));
        log.info("定时任务 getTrigger-> jobDataMap -> orderNo: {}",jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));
        log.info("定时任务执行，当前时间：" + DateUtil.formatDateTime(new Date()));

    }
}
