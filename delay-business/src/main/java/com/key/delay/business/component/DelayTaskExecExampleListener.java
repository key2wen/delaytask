package com.key.delay.business.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.key.delay.client.component.NotifyServerTaskBizSucComponent;
import com.key.facade.model.MqTaskExecMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * create by shuguang on 2017/11/20
 * <p>
 * 业务方自定义的消费端，对延迟任务开始执行操作，，
 */
public class DelayTaskExecExampleListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(DelayTaskExecExampleListener.class);

    @Autowired
    NotifyServerTaskBizSucComponent notifyServerTaskBizSucComponent;

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {

        for (MessageExt message : list) {

            String receivedMsg = new String(message.getBody());

            logger.info(">>>>DelayTaskExecExampleListener, receive message , id = " + message.getMsgId() + " , ip = "
                    + message.getBornHost() + " , content = " + receivedMsg);

            MqTaskExecMsg taskExecMsg = JSON.parseObject(receivedMsg, MqTaskExecMsg.class);

            if (taskExecMsg != null && taskExecMsg.getTaskId() != null) {

                if (!doBiz(taskExecMsg)) {
                    //todo 任务业务执行失败。。自己怎么处理都行呢。。
                } else {
                    //todo 执行成功了，，你想怎么处理？

                    //最后要通知server端任务执行成功了
                    if (!notifyServerTaskBizSucComponent.sendSucTask(taskExecMsg.getTaskId())) {
                        //todo 如果通知失败了，（一般是mq的原因造成通知失败了），处理方式：
                        /**
                         * 1. 不做处理：server端会继续发送不成功的任务交给业务方处理，所以业务方在处理任务执行时要做幂等
                         * 2. 处理：一般处理方式可以重发通知(失败通知最大上限次数自己定) ，为了及时告知server端任务已执行成功
                         */
                    }
                }
            }

        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public boolean doBiz(MqTaskExecMsg taskExecMsg) {

        //todo ,做自己的业务处理。。。

        //需要根据：taskExecMsg.getBizType() 和 taskExecMsg.getSeqId() 做幂等处理
        logger.info("====>doBiz, msg:{}", taskExecMsg);

        return true;

    }

}
