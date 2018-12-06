package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJobSimpleTrigger;
import com.jacky.quartz.job.HelloJobTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class HelloSchedulerDemoSimpleTrigger {
    /**
     * SimpleTrigger为需要在特定的日期/时间启动，且以一个可选的间隔时间重复执行n次的Job所设计
     * 案例一：表示在一个指定的时间段内，执行一次作业任务；
     * 案例二：在指定的时间间隔内多次执行作业任务。
     * 
     * 注意：
     * 1、simpleTrigger的属性有：开始时间，结束时间，重复次数，重复的时间间隔
     * 2、重复次数的属性值可以为正整数，或者框架定义好的常量 simpleTrigger.REPEAT_INDEFINITELY
     * 3、重复的时间间隔属性必须大于0或者为长整型的正整数，以秒，毫秒，分钟等作为时间单位
     * 4、如果同时指定结束时间和重复次数，则结束时间属性优先于重复次数属性。
     *   好处是当我们需要创建一个每间隔5秒触发一次直到结束时间的Trigger时，我们不需要去计算从开始到结束的重复次数。
    */
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
        JobDetail jobDetail = JobBuilder.newJob(HelloJobSimpleTrigger.class) //加载任务类，与HelloJob完成绑定
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
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        repeatForever().withIntervalInSeconds(5) //每隔5秒反复执行 
                        .withRepeatCount(2))   //反复执行3次，从0开始计数
                .build();
        
        //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
