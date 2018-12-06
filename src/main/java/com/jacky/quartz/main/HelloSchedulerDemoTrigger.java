package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJob;
import com.jacky.quartz.job.HelloJobTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class HelloSchedulerDemoTrigger {
    public static void main(String[] args) throws Exception {
        // 1:调度器(Scheduler)，从工厂中获得调度的实例（默认：实例化new StdSchedulerFactory();)
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        
        //设置开始任务时间
        Date startDate = new Date();
        //开始时间延时3秒 1秒为1000毫秒，因此这里为3000毫秒
        startDate.setTime(startDate.getTime() + 3000);
        //设置结束任务时间
        Date endDate = new Date();
        //结束时间为10秒之后
        endDate.setTime(endDate.getTime()+10000);
        
        // 2:任务实例(JobDetail)
        JobDetail jobDetail = JobBuilder.newJob(HelloJobTrigger.class) //加载任务类，与HelloJob完成绑定
                                .withIdentity("job1","group1") //参数一：任务名称，唯一，必要  参数2：任务组的名称
                                .usingJobData("message","print log")  //传递参数JobDataMap
                                .build();
        
        // 3:触发器(Trigger)
        // simpleTrigger: 
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")//参数一：触发器名称，唯一  参数2：触发器组的名称
                .usingJobData("message","simple trigger") //传递参数JobDataMap
                .startAt(startDate)  //开始时间
                .endAt(endDate)    //结束时间
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(5)) //每隔5秒反复执行
                .build();
        
        //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
