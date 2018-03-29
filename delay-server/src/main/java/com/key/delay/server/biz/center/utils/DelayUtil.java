package com.key.delay.server.biz.center.utils;

import com.key.delay.server.biz.model.DelayTask;
import com.key.facade.model.DelayTaskDTO;
import com.key.facade.model.MqTaskExecMsg;

/**
 * Created by shuguang on 17/11/21.
 */
public class DelayUtil {

    public static long getExecSecond(DelayTask task) {
        Long delaySecond = task.getDelay();
        if (task.getFailNum() != null && task.getFailNum() > 0) {
            delaySecond += (task.getTtr() * task.getFailNum());
        }
        return delaySecond + (task.getCreateTime().getTime() / 1000);
    }

    public static final String _split_ = "_sss_";

    public static String getTaskBody(DelayTaskDTO taskDto) {

        String body = taskDto.getBizType() + _split_ + taskDto.getSeqId() + _split_
                + (taskDto.getBody() == null ? "" : taskDto.getBody());
        return body;
    }

    public static MqTaskExecMsg getMqTask(DelayTask task) {

        MqTaskExecMsg mqTaskExecMsg = new MqTaskExecMsg();
        String[] sss = task.getBody().split(_split_);

        mqTaskExecMsg.setBizType(sss[0]);
        mqTaskExecMsg.setSeqId(sss[1]);
        if (sss.length > 2) {
            mqTaskExecMsg.setBody(sss[2]);
        } else {
            mqTaskExecMsg.setBody(null);
        }

        return mqTaskExecMsg;
    }


}
