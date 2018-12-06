package com.jacky.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * 对Trigger触发器的监听，需要实现TriggerListener
 * 
 *  * 全局监听器与非全局监听器：
 *  * 全局监听器能够接收到所有Job/Trigger的事件通知，而非全局监听器只能接收到在该非全局监听器上注册了的Job/Trigger的事件。
 */
public class MyTriggerListener implements TriggerListener {
    
    //构造方法，自定义传递的触发器的名称，默认是MyTriggerListener
    //可以通过构造函数，传递触发器的名字。
//    private String name;
//    public MyTriggerListener(String name) {
//        this.name = name;
//    }

    /**
     * 获得TriggerListener的名字
     * @return 返回JobListener的名字
     */
    @Override
    public String getName() {
        String name = this.getClass().getSimpleName();
        System.out.println("调用Trigger监听器的getName方法，获得TriggerListener的名字:"+name);
        return name;
    }

    /**
     * 当与监听器项关联的trigger被触发，job上的execute()方法将被执行时，调度器scheduler将调用此方法
     * @param trigger 
     * @param jobExecutionContext
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {

    }

    /**
     * Trigger触发后，job将要被执行时由调度器调用这个方法。
     * 此方法返回true则将否决该触发器触发的job的执行；返回false则job的执行不受影响，job正常执行。
     * @param trigger
     * @param jobExecutionContext
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false; //job没有被否决(veto)
    }

    /**
     * 在trigger错过触发时，调度器调用此方法。
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    /**
     *  trigger被触发且完成了job的执行后，调度器调用此方法。
     * @param trigger
     * @param jobExecutionContext
     * @param completedExecutionInstruction
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
