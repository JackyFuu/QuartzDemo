package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloSchedulerDemo {
    public static void main(String[] args) throws Exception {
        // 1:调度器(Scheduler)，从工厂中获得调度的实例（默认：实例化new StdSchedulerFactory();)
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 2:任务实例(JobDetail)
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class) //加载任务类，与HelloJob完成绑定
                                .withIdentity("job1","group1") //参数一：任务名称，唯一，必要  参数2：任务组的名称
                                .usingJobData("message","print log")  //传递参数JobDataMap
                                .build();
        //可以获得JobDetail的一些属性：name,group,jobClass，jobDataMap
        System.out.println("name:"+jobDetail.getKey().getName());
        System.out.println("group:"+jobDetail.getKey().getGroup());
        System.out.println("jobClass:"+jobDetail.getKey().getClass());
        
        // 3:触发器(Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")//参数一：触发器名称，唯一  参数2：触发器组的名称
                .usingJobData("message","simple trigger") //传递参数JobDataMap
                .startNow()  //马上启动触发器
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(5))
                .build();
        //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
