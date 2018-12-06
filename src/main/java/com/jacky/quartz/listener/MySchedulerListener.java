package com.jacky.quartz.listener;

import org.quartz.*;

/**
 * 对Scheduler调度器的监听，需要实现SchedulerListener
 * 
 * SchedulerListener会在Scheduler的生命周期中的关键事件发生时被调用
 * 包括增加一个job/trigger，删除一个job/trigger，scheduler发生错误，关闭scheduler等等。
 */
public class MySchedulerListener implements SchedulerListener {
    /**
     * job(jobDetail)被部署时调用
     * @param trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        
    }

    /**
     * job(jobDetail)被卸载时调用
     * @param triggerKey
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    /**
     * 
     * @param trigger
     */
    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    @Override
    public void triggersPaused(String s) {

    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    @Override
    public void triggersResumed(String s) {

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {

    }

    @Override
    public void jobDeleted(JobKey jobKey) {

    }

    @Override
    public void jobPaused(JobKey jobKey) {

    }

    @Override
    public void jobsPaused(String s) {

    }

    @Override
    public void jobResumed(JobKey jobKey) {

    }

    @Override
    public void jobsResumed(String s) {

    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
