package com.key.delay.server.biz.center.delay;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shuguang on 17/11/17.
 */

@Component
public interface DelayQueueComponent<T> {


    Long takeTask();

    List<Long> taskBatchTask();

    boolean addTask(T task);

}
