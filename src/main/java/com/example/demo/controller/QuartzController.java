package com.example.demo.controller;

import com.example.demo.service.impl.MyJobDetail;
import io.swagger.annotations.ApiOperation;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * QuartzController
 *
 * @author ZhangJP
 * @date 2020/11/3
 */
@Controller
public class QuartzController {

    private final Scheduler scheduler;

    @Autowired
    public QuartzController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("Quartz")
    @ApiOperation(value = "定时任务_创建", notes = "创建")
    @ResponseBody
    public Object quartz(@RequestParam("orderNo") String orderNo) throws Exception {
        //当前时间7秒之后
        Date start = new Date(System.currentTimeMillis() + 7 * 1000);

        /*
         通过JobBuilder.newJob()方法获取到当前Job的具体实现(以下均为链式调用)
         这里是固定Job创建，所以代码写死XXX.class
         如果是动态的，根据不同的类来创建Job，则 ((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass()
         即是 JobBuilder.newJob(((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass())


         usingJobData("name", "zy").usingJobData("age", 23) #给当前JobDetail添加参数，K V形式;给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getJobDetail().getJobDataMap().get("age")获取值
         withIdentity(orderNo) #添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种
         build() #执行
         */
        JobDetail jobDetail = JobBuilder.newJob(MyJobDetail.class)
                .usingJobData("name", "zy")
                .usingJobData("age", 23)
                .withIdentity(orderNo)
                .build();


        /*
        usingJobData("orderNo", orderNo) #给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值
        withIdentity(orderNo) #添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种
        startNow() #立即生效
        startAt(start) #开始执行时间
        endAt(start) #结束执行时间
        withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()) 添加执行规则:每隔1s执行一次,一直执行

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().usingJobData("orderNo", orderNo).withIdentity(orderNo).startAt(start).endAt(start).withSchedule(CronScheduleBuilder.cronSchedule("* 30 10 ? * 1/5 2018")).build(); #创建指定时间运行任务cronTrigger
        scheduler.scheduleJob(jobDetail, cronTrigger); #添加到定时任务中

         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .usingJobData("orderNo", orderNo)
                .withIdentity(orderNo)
                .startAt(start)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(3)
                                .repeatForever()
                )
                .build();
        //添加定时任务
        scheduler.scheduleJob(jobDetail, trigger);

        if (!scheduler.isShutdown()) {
            //启动
            scheduler.start();
        }
        System.err.println("----定时任务启动成功； " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ------------");
        return "ok";
    }

    @PostMapping("shutdown")
    @ApiOperation(value = "定时任务_停止", notes = "停止")
    @ResponseBody
    public Object shutdown(@RequestParam("orderNo") String orderNo) throws SchedulerException {
        //暂停Trigger
        scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));
        return "";
    }

    @PostMapping("resume")
    @ApiOperation(value = "定时任务_恢复", notes = "恢复")
    @ResponseBody
    public Object resume(@RequestParam("orderNo") String orderNo) throws SchedulerException {
        //恢复Trigger
        scheduler.resumeTrigger(TriggerKey.triggerKey(orderNo));
        return "ok";
    }

    @PostMapping("del")
    @ApiOperation(value = "定时任务_删除", notes = "删除")
    @ResponseBody
    public Object del(@RequestParam("orderNo") String orderNo) throws SchedulerException {
        //暂停触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));
        //移除触发器
        scheduler.unscheduleJob(TriggerKey.triggerKey(orderNo));
        //删除Job
        scheduler.deleteJob(JobKey.jobKey(orderNo));
        return "ok";
    }

}
