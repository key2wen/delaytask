package com.key.delay.server.biz.center.execute;

/**
 * Created by shuguang on 17/11/17.
 */

import com.key.delay.server.biz.center.delay.DelayQueueComponent;
import com.key.delay.server.biz.center.message.MessageComponent;
import com.key.delay.server.biz.center.utils.DelayUtil;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.facade.constants.DelayTaskStatusConstants;
import com.key.facade.request.DelayTaskComQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

/**
 * 获取延迟任务数据业务逻辑
 */
public class FetchTask2DelayQueueThread implements Runnable {

    Logger logger = LoggerFactory.getLogger("swallow");

    protected DelayTaskService delayTaskService;

    protected DelayQueueComponent<DelayTask> delayQueueComponent;

    protected MessageComponent messageComponent;

    protected boolean testing = false;


    //最多能放入延迟的次数
    private static final int MAX_DELAY_SIZE = 5;

    public FetchTask2DelayQueueThread() {
    }

    public FetchTask2DelayQueueThread(DelayTaskService delayTaskService,
                                      DelayQueueComponent delayQueueComponent,
                                      MessageComponent messageComponent, boolean test) {
        this.delayTaskService = delayTaskService;
        this.delayQueueComponent = delayQueueComponent;
        this.messageComponent = messageComponent;
        this.testing = test;
    }

    @Override
    public void run() {
        // fetch delayTask data from db

        int row = 50;
        int offset = 0;

        Long nowTimeSed = Calendar.getInstance().getTimeInMillis() / 1000;

//        logger.info("=======>fetchTask2DelayQueue Starting...,offset:{},row:{},nowTime:{}",
//                new Object[]{offset, row, nowTimeSed});

        while (true) {

            try {
                DelayTaskComQuery query = new DelayTaskComQuery();
                //当前时间需要 大于 下次执行时间
                query.setNextExecTime(nowTimeSed);
                query.setLimit(row);
                query.setOffset(offset);
                query.setStatus(DelayTaskComQuery.NOT_FINISHED);

                List<DelayTask> taskList = delayTaskService.queryCom(query);

                if (testing) {
                    logger.info("======>fetchTask2DelayQueue Exception Testing...");
                    throw new NullPointerException("test NullPointerException");
                }

                if (CollectionUtils.isEmpty(taskList)) {
                    break;
                }

                logger.info("=======>fetchTask2DelayQueue...offset:{},row:{},nowTime:{},querySize:{}",
                        new Object[]{offset, row, nowTimeSed, taskList.size()});

                // to delay queue
                task2DelayQueue(taskList);

                if (taskList.size() < row) {
                    break;
                }
                offset += row;
            } catch (Throwable th) {
                logger.error("*****************>>>fetchTask2DelayQueue, error:{}", th.getMessage(), th);
            }
        }

    }

    private void task2DelayQueue(List<DelayTask> taskList) {

        for (DelayTask task : taskList) {

            if (task.getFailNum() != null && task.getFailNum() >= MAX_DELAY_SIZE) {
                //todo 报警。。
                logger.warn("task_failNum reach maxSize, taskId:{},failNum:{}", task.getId(), task.getFailNum());

                setTaskTimeOut(task);

                sendMessage(task);

                continue;
            }

            task.setFailNum(task.getFailNum() == null ? 1 : (task.getFailNum() + 1));
            task.setNextExecTime(DelayUtil.getExecSecond(task));

            if (delayQueueComponent.addTask(task)) {
                //update db status
                setTaskNextExecBiz(task);
            }
        }

    }

    private void setTaskNextExecBiz(DelayTask task) {
        DelayTask updTask = new DelayTask();
        updTask.setId(task.getId());

        /**
         * update 2017/11/24 不修改状态，为了防止把成功的状态又修改成失败了，增加操作成本
         * ， 可以根据failNum判断操作情况
         * 所以目前status只会有：0/1(初始)， 4(成功)， 5(超次关闭) 三种状态
         */
//        updTask.setStatus(DelayTaskStatusConstants.RESERVE_DEPAY);

        updTask.setFailNum(task.getFailNum());
        updTask.setNextExecTime(task.getNextExecTime());

        int upd = delayTaskService.updateById(updTask);
        if (upd <= 0) {
            logger.warn("swallowDelayTask update failed, taskId:{}, failNum:{}", updTask.getId(), updTask.getFailNum());
        }
    }

    private void setTaskTimeOut(DelayTask task) {
        DelayTask timeOutTask = new DelayTask();
        timeOutTask.setStatus(DelayTaskStatusConstants.TIME_OUT_CLOSE);
        timeOutTask.setId(task.getId());
        delayTaskService.updateById(timeOutTask);
    }

    private void sendMessage(DelayTask task) {
        messageComponent.sendMail(task);
    }
}
