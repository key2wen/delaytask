package com.key.delay.client.component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.key.facade.model.MqTaskBizSucMsg;
import com.key.jorigin.mq.rocketmq.RocketMqProducer;
import com.key.jorigin.mq.rocketmq.msg.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * create by shuguang
 * <p>
 * 业务方通过
 *
 * @Autowired NotifyTaskBizSucComponent notifyTaskBizSucComponent
 * notifyTaskBizSucComponent.sendSucTask(taskId);
 * 这种方法 把执行成功的任务 告知server端， (server端会有消费端消费该消息)
 */
@Component
public class NotifyServerTaskBizSucComponent {

    private static final Logger logger = LoggerFactory.getLogger(NotifyServerTaskBizSucComponent.class);

    @Autowired
    @Qualifier("notifyServerTaskBizSucProducer")
    RocketMqProducer notifyManager;

    private static final String tag = "taskBizSuc";

    public boolean sendSucTask(Long taskId) {

        if (taskId == null || taskId <= 0) {
            logger.warn("notifyServerTaskBizSucProducer Null message body, taskId is null!");
            return false;
        }

        MqTaskBizSucMsg msg = new MqTaskBizSucMsg(taskId + "");

        try {

            StringMessage stringMessage = new StringMessage(msg.getTaskId(), tag, JSONObject.toJSONString(msg));
            SendResult sendResult = notifyManager.sendMessage(stringMessage);
            logger.info("notifyServerTaskBizSucProducer sent to the queue successfully[msgId={}，sendStatus={}]：，taskId={}",
                    new Object[]{sendResult.getMsgId(), sendResult.getSendStatus(), msg.getTaskId()});

            return true;
        } catch (Exception e) {
            logger.error("notifyServerTaskBizSucProducer Putting message into send queue failed：，taskId={}", msg.getTaskId(), e);
            return false;
        }
    }


}
