package com.jacky.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 对Job任务的监听，需要实现JobListener
 * 
 * 全局监听器与非全局监听器：
 * 全局监听器能够接收到所有Job/Trigger的事件通知，而非全局监听器只能接收到在该非全局监听器上注册了的Job/Trigger的事件。
 */

public class MyJobListener implements JobListener {

    /**
     * 获得JobListener的名字
     * @return 返回JobListener的名字
     */
    @Override
    public String getName() {
        String name = this.getClass().getSimpleName();
        System.out.println("调用Job监听器的getName方法，获得了JobListener的名字:"+name);
        return name;
    }

    /**
     * 在Job将要被执行时，调用此方法
     * @param jobExecutionContext job执行的上下文
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("在任务："+name+"将要被执行时，调用了jobToBeExecuted方法");
    }

    /**
     * JobDetail(相当于job的封装)即将被执行，但是被TriggerListener否决(vetoed)时调用此方法
     * @param jobExecutionContext job执行的上下文
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("任务："+name + "即将被执行，但是被TriggerListener否决(vetoed)时调用了jobExecutionVetoed方法");
    }

    /**
     *  JobDetail被执行之后，调用此方法
     * @param jobExecutionContext job执行的上下文
     * @param e Job执行异常
     */
    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("任务："+name + "被执行之后，调用了jobWasExecuted()");
    }
}
