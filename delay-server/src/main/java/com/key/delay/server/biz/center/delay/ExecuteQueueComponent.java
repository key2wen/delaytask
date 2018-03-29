package com.key.delay.server.biz.center.delay;

import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/17.
 * <p>
 * 可执行的任务队列,准备执行
 */

@Component
public interface ExecuteQueueComponent<T> {


    /**
     * @param taskId
     * @return
     */
    boolean addExecuteTask(T taskId);

    T takeExecuteTask();

}
