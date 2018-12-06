package com.jacky.quartz.main;

import com.jacky.quartz.job.HelloJobCronTrigger;
import com.jacky.quartz.job.HelloJobSimpleTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloSchedulerDemoCronTrigger {
    /**
     * CronTrigger触发器提供按日程来触发任务的方法，它是基于日历的作业调度器
     * 通过指定Cron表达式，就可以执行日程任务。
     *
     */
    public static void main(String[] args) throws Exception {
        // 1:调度器(Scheduler)，从工厂中获得调度的实例（默认：实例化new StdSchedulerFactory();)
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //也可以这样创建调度器
//        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = stdSchedulerFactory.getScheduler();

//        //设置开始任务时间
//        Date startDate = new Date();
//        //开始时间延时3秒 1秒为1000毫秒，因此这里为3000毫秒
//        startDate.setTime(startDate.getTime() + 3000);
//        //设置结束任务时间
//        Date endDate = new Date();
//        //结束时间为10秒之后
//        endDate.setTime(endDate.getTime()+10000);
        
        // 2:任务实例(JobDetail)
        JobDetail jobDetail = JobBuilder.newJob(HelloJobCronTrigger.class) //加载任务类，与HelloJob完成绑定
                                .withIdentity("job1","group1") //参数一：任务名称，唯一，必要  参数2：任务组的名称
                                .usingJobData("message","print log")  //传递参数JobDataMap
                                .build();
        
        // 3:触发器(Trigger)
        // CronTrigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")//参数一：触发器名称，唯一  参数2：触发器组的名称
                .usingJobData("message","simple trigger") //传递参数JobDataMap
                //.startAt(startDate)  //开始时间
                //.endAt(endDate)    //结束时间  
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * 6 12 ?"))   //日历计划执行， 12月6日每5秒执行一次
                .build();
        
        //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        Date date = scheduler.scheduleJob(jobDetail, trigger);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        System.out.println("调度器开始时间为："+dateFormat.format(date)); //获取调度器的开始时间，使得任务和触发器进行关联
        
        //启动调度
        scheduler.start();
        //scheduler执行两秒后暂停
        Thread.sleep(2000);
        //挂起\暂停
        scheduler.standby();
        //再过5秒后开始执行
        Thread.sleep(5000);
        //再次启动调度
        scheduler.start();
        //关闭任务调度
        scheduler.shutdown();  //关闭之后，不能够再次启动
        //shutdown(true) 等待所有任务执行完毕之后，再关闭任务调度
        //shutdown(false) 直接关闭任务调度
    }
}
