package com.key.delay.server.biz.center.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.facade.constants.DelayTaskStatusConstants;
import com.key.facade.model.MqTaskBizSucMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * create by shuguang on 2017/11/20
 * <p>
 * 业务执行成功通知消息
 * <p>
 * 任务对应的业务执行成功后，通过mq发送通知，修改任务状态为成功
 */
public class TaskSucNotifyServerMessageListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger("task_notify");

    @Autowired
    DelayTaskService delayTaskService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {

        for (MessageExt message : list) {

            String receivedMsg = new String(message.getBody());

            logger.info(">>>>TaskSucNotifyServerMessageListener, receive message , id = " + message.getMsgId() + " , ip = "
                    + message.getBornHost() + " , content = " + receivedMsg);

            MqTaskBizSucMsg obj = JSON.parseObject(receivedMsg, MqTaskBizSucMsg.class);

            if (!this.updateMsgSuc(obj)) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private boolean updateMsgSuc(MqTaskBizSucMsg mqTaskBizSucMsg) {

        DelayTask task = new DelayTask();

        task.setStatus(DelayTaskStatusConstants.FINISH);

        try {
            task.setId(Long.parseLong(mqTaskBizSucMsg.getTaskId()));
            delayTaskService.updateById(task);
            return true;
        } catch (Exception e) {
            logger.error("TaskSucNotifyServer updateTaskStatus error,taskId:{}", task.getId(), e);
            return false;
        }
    }

}
