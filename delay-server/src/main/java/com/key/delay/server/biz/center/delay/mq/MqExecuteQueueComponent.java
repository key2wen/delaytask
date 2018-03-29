package com.key.delay.server.biz.center.delay.mq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.key.delay.server.biz.center.delay.ExecuteQueueComponent;
import com.key.delay.server.biz.center.exception.DelayTaskRuntimeException;
import com.key.delay.server.biz.center.utils.DelayUtil;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.facade.constants.DelayTaskStatusConstants;
import com.key.facade.model.MqTaskExecMsg;
import com.key.jorigin.mq.rocketmq.RocketMqProducer;
import com.key.jorigin.mq.rocketmq.msg.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/20.
 * <p>
 * 可执行的任务队列,准备执行
 */

@Component
public class MqExecuteQueueComponent implements ExecuteQueueComponent<Long> {

    @Autowired
    DelayTaskService delayTaskService;

    @Autowired
    DelayFlowService delayFlowService;

    @Autowired
    @Qualifier("delayTaskExecQueueProducer")
    RocketMqProducer notifyManager;

    Logger logger = LoggerFactory.getLogger("swallow");

    /**
     * @param taskId
     * @return
     */
    @Override
    public boolean addExecuteTask(Long taskId) {
        return sendTaskToMq(taskId);
    }

    @Override
    public Long takeExecuteTask() {
        /**
         * mq的方式，获取任务直接在业务方进行消费
         */
        throw new DelayTaskRuntimeException("mq方式不支持server端获取task");
    }

    public boolean sendTaskToMq(Long taskId) {

        if (taskId == null || taskId <= 0) {
            logger.warn("delayTaskExecQueueProducer Null message body, taskId is null!");
            return false;
        }

        DelayTask delayTask = delayTaskService.findById(taskId);
        if (delayTask == null) {
            logger.warn("delayTaskExecQueueProducer taskId:{},没有查询到任务信息", taskId);
            //这种情况就当执行成功了，返回true
            return true;
        }
        if (delayTask.getStatus() != null && (delayTask.getStatus() == DelayTaskStatusConstants.FINISH
                || delayTask.getStatus() == DelayTaskStatusConstants.TIME_OUT_CLOSE)) {

            logger.warn("delayTaskExecQueueProducer taskId:{}, status is ok:{}", taskId, delayTask.getStatus());
            return true;
        }

        //update 2017/11/24 不再获取类型和序列号，需要的话，提交任务的时候可以放到body中
//        DelayFlowQuery flowQuery = new DelayFlowQuery();
//        flowQuery.setTaskId(taskId);
//        flowQuery.setOffset(0);
//        flowQuery.setLimit(1);
//        List<DelayFlow> flowList = delayFlowService.query(flowQuery);
//        if (CollectionUtils.isEmpty(flowList)) {
//            logger.warn("delayTaskExecQueueProducer taskId:{},select flow is empty", taskId);
//            return true;
//        }

        MqTaskExecMsg execMsg = DelayUtil.getMqTask(delayTask);

//        execMsg.setBody(delayTask.getBody());
//        execMsg.setBizType(flowList.get(0).getBizType());
//        execMsg.setSeqId(flowList.get(0).getSeqId());

        execMsg.setTaskId(taskId);
        execMsg.setTag(delayTask.getTopic());

        try {

            StringMessage stringMessage = new StringMessage(execMsg.getTaskId() + "", execMsg.getTag(), JSONObject.toJSONString(execMsg));
            SendResult sendResult = notifyManager.sendMessage(stringMessage);
            logger.info("delayTaskExecQueueProducer sent to the queue successfully[msgId={}，sendStatus={}]：，taskId={}",
                    new Object[]{sendResult.getMsgId(), sendResult.getSendStatus(), execMsg.getTaskId()});

            return true;
        } catch (Exception e) {
            logger.error("delayTaskExecQueueProducer Putting message into send queue failed：，taskId={}", execMsg.getTaskId(), e);
            return false;
        }
    }

}
