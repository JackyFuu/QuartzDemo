package com.jacky.quartz.job;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@PersistJobDataAfterExecution  //多次调用job时，会对job进行持久化，即保存一些jobDataMap的数据信息，例如这里的count
public class HelloJob implements Job {

    /**
     * Job实例在quartz中的生命周期：每次调度器执行job时，它在调用execute方法之前会常见一个新的Job实例，
     * 当调用完成时，关联的job实例对象会被释放，释放的实例会被垃圾回收机制回收
     */
//    public HelloJob(){
//        System.out.println("欢迎使用job!");
//    }
    /**
     * 使用添加setter方法对应JobDataMap的键值，quartz框架默认的JobFactory实现类在初始化
     * Job实例对象时会自动地调用这些setter方法
      */        
    private String message;
    public void setMessage(String message){
        this.message = message;
    }

    private Integer count;

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @param jobExecutionContext
     * 当Scheduler调用一个job时，就会将jobExecutionContext传递给Job的execute()方法；
     * Job能够通过jobExecutionContext对象访问到quartz运行时候的环境以及Job本身的明细数据
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //输出当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        
        //通过JobExecutionContext获取JobDetail的内容:name, group, class等等
//        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
//        System.out.println("工作任务名称："+ jobKey.getName()+";工作任务组："+jobKey.getGroup());
//        System.out.println("工作类名称：(带包名)"+ jobKey.getClass().getName());
//        System.out.println("工作类名称：(不带包名)"+ jobKey.getClass().getSimpleName());
        //通过JobExecutionContext获取Trigger的内容
//        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
//        System.out.println("触发器名称："+ triggerKey.getName()+";触发器组："+triggerKey.getGroup());
        
        // 从JobDetail对象中获取jobDataMap数据
//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        String jobDataMessage = jobDataMap.getString("message");
//        System.out.println("任务数据的参数值："+ jobDataMessage);
        // 从Trigger对象中获取jobDataMap数据
//        JobDataMap triggerDataMap = jobExecutionContext.getTrigger().getJobDataMap();
//        String triggerDataMessage = triggerDataMap.getString("message");
//        System.out.println("触发器数据的参数值："+ triggerDataMessage);

        /**
         * 如果遇到同名的key，trigger中的.usingJobData("message","simple触发器")会覆盖JobDetail中
         * 的.usingJobData("message","打印日志")。
         */
        //从setter方法中读取message的值
//        System.out.println("参数值：" + message);
        //获取其他内容
//        System.out.println("当前任务的执行时间："+ jobExecutionContext.getFireTime()); //当前任务的执行时间
//        System.out.println("下次任务的执行时间："+ jobExecutionContext.getNextFireTime()); //下次任务的执行时间
        
        
        //工作内容
        System.out.println("正在进行数据库的备份工作，备份数据库的时间是："+dateString);
        
        //测试PersistJobDataAfterExecution注解  
        // 如果一个Job类有上面的这个注解则这个Job是有状态Job,如果没有，则为无状态Job
        // 有状态Job多次调用job时，会对job进行持久化，即保存一些jobDataMap的数据信息，例如这里的count
        // 而无状态Job不会保存任何数据，每次都是重新创建一个新的Job
        
        // 对于这个Job,如果没有@PersistJobDataAfterExecution注解，则在每次调用Job时count的值总为1
        // 反之每次调用Job时都会使用之前Job保存下来的值，现象是每次执行，count递增1个单温
        count++;
        System.out.println("count数量："+count);
        jobExecutionContext.getJobDetail().getJobDataMap().put("count", count);
    }
}
