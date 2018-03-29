package com.key.delay.server.biz.core.impl;

import com.key.delay.server.biz.center.delay.redis.JedisDelayQueueComponent;
import com.key.delay.server.biz.center.utils.DelayUtil;
import com.key.delay.server.biz.core.TaskManageCoreService;
import com.key.delay.server.biz.model.DelayFlow;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.facade.constants.DelayTaskStatusConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskManageCoreServiceImpl implements TaskManageCoreService {
    private static final Logger logger = LoggerFactory.getLogger(TaskManageCoreServiceImpl.class);

    @Autowired
    private DelayTaskService delayTaskService;

    @Autowired
    private DelayFlowService delayFlowService;

    @Autowired
    private JedisDelayQueueComponent delayQueueComponent;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, timeout = 30, rollbackFor = Exception.class)
    public void addNewDelayTask(DelayFlow delayFlow, DelayTask delayTask) {

        int flowInsertResult = delayFlowService.insert(delayFlow);
        if (flowInsertResult <= 0) {
            throw new RuntimeException(String.format("新增任务记录异常：bizType=%s,seq=%s,body=%s", delayFlow.getBizType(),
                    delayFlow.getSeqId(), delayTask.getBody()));
        }

        //先修改状态，因为在一个事物中执行，能保证同步
        delayTask.setStatus(DelayTaskStatusConstants.NORMAL_DELAY);
        delayTask.setNextExecTime(DelayUtil.getExecSecond(delayTask));

        int taskInsertRestult = delayTaskService.insert(delayTask);
        if (taskInsertRestult <= 0) {
            throw new RuntimeException(String.format("新增任务异常：bizType=%s,seq=%s,body=%s", delayFlow.getBizType(),
                    delayFlow.getSeqId(), delayTask.getBody()));
        }

        DelayFlow updateStatus = new DelayFlow();
        updateStatus.setTaskId(delayTask.getId());
        updateStatus.setId(delayFlow.getId());
        int updateFlow = delayFlowService.updateById(updateStatus);
        if (updateFlow <= 0) {
            throw new RuntimeException(String.format("更新任务流水异常：bizType=%s,seq=%s,body=%s", delayFlow.getBizType(),
                    delayFlow.getSeqId(), delayTask.getBody()));
        }

        /**
         * .数据库操作成功后，再放入队列
         */
        if (!delayQueueComponent.addTask(delayTask)) {
            throw new RuntimeException(String.format("新增任务放入DelayQueue异常：bizType=%s,seq=%s,body=%s", delayFlow.getBizType(),
                    delayFlow.getSeqId(), delayTask.getBody()));
        }

    }

}
