package com.key.delay.server.biz.service.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.key.delay.server.biz.center.utils.DelayUtil;
import com.key.delay.server.biz.core.TaskManageCoreService;
import com.key.delay.server.biz.model.DelayFlow;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskManageService;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.facade.constants.DelayTaskStatusConstants;
import com.key.facade.model.DelayTaskDTO;
import com.key.facade.request.DelayFlowQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class DelayTaskManageServiceImpl implements DelayTaskManageService {

    /**
     * 最大延迟30天
     */
    private static final Integer MAX_DELAY_TIME = 60 * 60 * 24 * 30;

    /**
     * 最大消费超时1天
     */
    private static final Integer MAX_TTL_TIME = 60 * 60 * 24;

    @Autowired
    private DelayFlowService delayFlowService;

    @Autowired
    private TaskManageCoreService taskManageCoreService;

    @Override
    public long submitTask(DelayTaskDTO taskDto) {

        /**
         * 1.参数校验
         */
        this.paramCheck(taskDto);

        /**
         * 2.幂等校验
         */
        DelayFlowQuery query = new DelayFlowQuery();
        query.setBizType(taskDto.getBizType());
        query.setSeqId(taskDto.getSeqId());
        List<DelayFlow> delayFlowList = delayFlowService.query(query);
        if (!CollectionUtils.isEmpty(delayFlowList)) {
            throw new RuntimeException(String.format("当前延迟任务已存在：bizType=%s,seqId=%s",
                    taskDto.getBizType(), taskDto.getSeqId()));
        }

        /**
         * 3.新增任务 job pool
         */
        DelayFlow delayFlow = buildSwallowFlow(taskDto);
        DelayTask delayTask = buildSwallowDelayTask(taskDto);
        taskManageCoreService.addNewDelayTask(delayFlow, delayTask);

        return delayTask.getId();
    }

    private DelayFlow buildSwallowFlow(DelayTaskDTO task) {
        DelayFlow delayFlow = new DelayFlow();
        delayFlow.setBizType(task.getBizType());
        // delayFlow.setCreateTime(createTime);
        // delayFlow.setId(id);
        // delayFlow.setModifyTime(modifyTime);
        delayFlow.setSeqId(task.getSeqId());
        // delayFlow.setTaskId(taskId);

        return delayFlow;
    }

    private DelayTask buildSwallowDelayTask(DelayTaskDTO task) {
        DelayTask delayTask = new DelayTask();

        delayTask.setBody(DelayUtil.getTaskBody(task));

        //先设置好创建时间，为了后面放到delayQueue中排分
        delayTask.setCreateTime(new Date());
        delayTask.setDelay(task.getDelay());
        // delayTask.setId(id);
        // delayTask.setModifyTime(modifyTime);
        delayTask.setRemark(task.getRemark());
        delayTask.setStatus(DelayTaskStatusConstants.PENDING);
        delayTask.setTopic(task.getTopic());
        delayTask.setTtr(task.getTtr());

        delayTask.setFailNum(0);

        return delayTask;
    }

    private void paramCheck(DelayTaskDTO task) {
        Assert.notNull(task, "任务不能为空");
        Assert.notNull(task.getBizType(), "业务类型不能为空");
        Assert.notNull(task.getBody(), "业务内容不能为空");
        Assert.notNull(task.getDelay(), "延迟时间不能为空");
        Assert.notNull(task.getSeqId(), "任务序列号不能为空");
        Assert.notNull(task.getTopic(), "topic不能为空");
        Assert.notNull(task.getTtr(), "消费超时时间不能为空");

        Long delayTime = task.getDelay();
        if (delayTime > MAX_DELAY_TIME) {
            throw new IllegalArgumentException(String.format("任务超过最大延迟时间:%s", MAX_DELAY_TIME));
        }

        Long ttr = task.getTtr();
        if (ttr > MAX_TTL_TIME) {
            throw new IllegalArgumentException(String.format("任务超过最大消费超时时间:%s", MAX_TTL_TIME));
        }

    }

}
