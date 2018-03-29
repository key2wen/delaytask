package com.key.facade.model;

import java.io.Serializable;

/**
 * create by shuguang
 * <p>
 * 任务执行成功后 通知server 的消息实体
 */
public class MqTaskBizSucMsg implements Serializable {

    private String taskId;

    public MqTaskBizSucMsg() {
    }

    public MqTaskBizSucMsg(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
