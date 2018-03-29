package com.key.facade.model;

import java.io.Serializable;

/**
 * create by shuguang
 * <p>
 * 任务到时间后，server通知业务方执行的消息实体
 */
public class MqTaskExecMsg implements Serializable {

    /**
     * 业务类型 (对应通知到时间的任务的消费方的 tag, 请合理定义，)
     */
    private String bizType;

    /**
     * 业务订单号:通过业务类型和工具生成
     */
    private String seqId;

    private Long taskId;

    private String tag;

    /**
     * 自定义任务内容json
     */
    private String body;

    public MqTaskExecMsg() {
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return "MqTaskExecMsg{" +
                "bizType='" + bizType + '\'' +
                ", seqId='" + seqId + '\'' +
                ", taskId=" + taskId +
                ", tag='" + tag + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
