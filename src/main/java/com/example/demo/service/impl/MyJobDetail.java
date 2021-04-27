package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
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
public class MyJobDetail implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.err.println(jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
        System.err.println(jobExecutionContext.getJobDetail().getJobDataMap().get("age"));
        System.err.println(jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));
        System.err.println("定时任务执行，当前时间：" + DateUtil.formatDateTime(new Date()));

    }
}
