package com.key.delay.server.biz.center.execute;

/**
 * Created by shuguang on 17/11/18.
 */


import com.key.delay.server.biz.center.BizTask;
import com.key.delay.server.biz.center.delay.ExecuteQueueComponent;

/**
 * 从可执行任务获取任务执行...
 */
public class ExecutingTaskThread implements Runnable {


    protected ExecuteQueueComponent<Long> executeQueueComponent;

    protected BizTask bizTask;

    public ExecutingTaskThread() {
    }

    public ExecutingTaskThread(BizTask bizTask,
                               ExecuteQueueComponent<Long> executeQueueComponent) {
        this.bizTask = bizTask;
        this.executeQueueComponent = executeQueueComponent;
    }

    @Override
    public void run() {

        while (true) {

            Long taskId = executeQueueComponent.takeExecuteTask();
            if (taskId == null || taskId <= 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            boolean suc = bizTask.execute(taskId);
            if (!suc) {
                //把任务放回去再执行一次
//                executeQueueComponent.add(taskId);
            }
        }

    }

}
