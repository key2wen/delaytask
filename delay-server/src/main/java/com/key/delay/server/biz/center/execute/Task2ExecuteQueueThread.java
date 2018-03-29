package com.key.delay.server.biz.center.execute;

/**
 * Created by shuguang on 17/11/17.
 */

import com.key.delay.server.biz.center.delay.DelayQueueComponent;
import com.key.delay.server.biz.center.delay.ExecuteQueueComponent;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从延迟队列获取可执行任务，放入可执行队列
 */
public class Task2ExecuteQueueThread implements Runnable {

    Logger logger = LoggerFactory.getLogger("swallow");

    protected DelayTaskService delayTaskService;

    protected DelayQueueComponent<DelayTask> delayQueueComponent;

    protected ExecuteQueueComponent<Long> executeQueueComponent;

    AtomicBoolean finished = new AtomicBoolean(true);

    public Task2ExecuteQueueThread() {
    }

    public Task2ExecuteQueueThread(DelayTaskService delayTaskService,
                                   DelayQueueComponent delayQueueComponent, ExecuteQueueComponent executeQueueComponent) {
        this.delayTaskService = delayTaskService;
        this.delayQueueComponent = delayQueueComponent;
        this.executeQueueComponent = executeQueueComponent;
    }

    @Override
    public void run() {

        // fetch Task from delayQueue to executingQueue
//        logger.info("======>task2ExecQueue start.....");

        //上一次还没执行，则这次不执行
        if (!finished.compareAndSet(true, false)) {
            return;
        }

        while (fetchTask2ExeQueue()) ;

        finished.set(true);


    }

    private boolean fetchTask2ExeQueue() {

        List<Long> taskIdList;
        try {
            taskIdList = delayQueueComponent.taskBatchTask();
            if (CollectionUtils.isEmpty(taskIdList)) {
                //结束循环
                return false;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        logger.error("task2ExecuteQueueThread sleep error:{}", e.getMessage(), e);
//                    }
            }

            logger.info("task2ExecQueue, taskBatchTask size:{}", taskIdList.size());

            for (Long taskId : taskIdList) {

                logger.info("task2ExecQueue, taskId:{}", taskId);

                if (executeQueueComponent.addExecuteTask(taskId)) {
                    DelayTask updateTask = new DelayTask();
                    updateTask.setId(taskId);
                    /**
                     *
                     * update 2017/11/24 不修改状态（只修改更新时间），为了防止把成功的状态又修改成失败了，增加操作成本
                     *
                     * 所以目前status只会有：0/1(初始)， 4(成功)， 5(超次关闭) 三种状态
                     */
//                        updateTask.setStatus(DelayTaskStatusConstants.READY);

                    if (delayTaskService.updateById(updateTask) <= 0) {
                        logger.warn("task2ExecuteQueueThread updateTask failed,taskId:{}", taskId);
                    }
                }
            }
            return true;

        } catch (Throwable th) {
            logger.error("*****************>>>task2ExecQueue, error:{}", th.getMessage(), th);
//            if (taskIdList == null) {
//                //可能没有可执行任务数据 or 获取可执行任务数据过程中异常了， 这两种情况都结束循环
//            }
            return false;
        }
    }

}
