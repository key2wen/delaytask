package com.key.delay.test;

import com.key.delay.client.listener.DefaultDelayTaskExecListener;
import com.key.facade.model.MqTaskExecMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by shuguang on 2017/11/22
 * <p>
 * 业务方自定义的消费端，对延迟任务开始执行操作，，
 */
public class DelayTaskExecDefaultListener extends DefaultDelayTaskExecListener {

    private static final Logger logger = LoggerFactory.getLogger(DelayTaskExecDefaultListener.class);

    @Override
    public boolean doBiz(MqTaskExecMsg taskExecMsg) {
        //todo ,做自己的业务处理。。。

        //需要根据：taskExecMsg.getBizType() 和 taskExecMsg.getSeqId() 做幂等处理
        return false;
    }


}
