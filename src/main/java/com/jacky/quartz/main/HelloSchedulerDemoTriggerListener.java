package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJobListener;
import com.jacky.quartz.listener.MyJobListener;
import com.jacky.quartz.listener.MyTriggerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;

public class HelloSchedulerDemoTriggerListener {
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
        
        //全局监听器与非全局监听器：
        //--全局监听器能够接收到所有Job/Trigger的事件通知，
        //--而非全局监听器只能接收到在该非全局监听器上注册了的Job/Trigger的事件。
        
        //创建并注册一个全局TriggerListener
//        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(),
//                EverythingMatcher.allTriggers());
        //创建并注册一个局部TriggerListener,指定触发器Trigger
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(),
                KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1","group1")));
        //开启调度任务
        scheduler.start();
    }
}
