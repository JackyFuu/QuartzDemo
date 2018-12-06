package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJobListener;
import com.jacky.quartz.listener.MySchedulerListener;
import com.jacky.quartz.listener.MyTriggerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;

public class HelloSchedulerDemoSchedulerListener {
    public static void main(String[] args) throws Exception {
        // 1:调度器(Scheduler)，从工厂中获得调度的实例（默认：实例化new StdSchedulerFactory();)
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail jobDetail = JobBuilder.newJob(HelloJobListener.class) //加载任务类，与HelloJob完成绑定
                                .withIdentity("job1","group1") //参数一：任务名称，唯一，必要  参数2：任务组的名称
                                .usingJobData("message","print log")  //传递参数JobDataMap
                                .build();
        
        // 3:触发器(Trigger)
        // simpleTrigger: 
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")//参数一：触发器名称，唯一  参数2：触发器组的名称
                .usingJobData("message","simple trigger") //传递参数JobDataMap
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever().withIntervalInSeconds(5) //每隔5秒反复
                        .withRepeatCount(2)) //执行执行三次,从0开始计数
                .build();
        
        //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(jobDetail, trigger);
        
        //创建调度器的监听
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
        
        //删除调度器的监听
//        scheduler.getListenerManager().removeSchedulerListener(new MySchedulerListener());
        
        //开启调度任务
        scheduler.start();
    }
}
