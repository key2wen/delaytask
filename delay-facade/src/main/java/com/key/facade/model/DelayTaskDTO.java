package com.key.facade.model;

import java.io.Serializable;

public class DelayTaskDTO implements Serializable {

    private static final long serialVersionUID = -732788010797386201L;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务订单号:通过业务类型和工具生成
     */
    private String seqId;

    /**
     * 任务分组
     * (对应通知到时间的任务的消费方的 tag, 请合理定义，可以和bizType一样)
     */
    private String topic;

    /**
     * 延迟时间(秒)
     */
    private Long delay;

    /**
     * 任务执行超时时长（秒）
     */
    private Long ttr;

    /**
     * 自定义任务内容json
     */
    private String body;

    /**
     * 备注
     */
    private String remark;

    DelayTaskDTO() {
    }


    public String getBizType() {
        return bizType;
    }

    public String getSeqId() {
        return seqId;
    }

    public String getTopic() {
        return topic;
    }


    public String getBody() {
        return body;
    }

    public String getRemark() {
        return remark;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Long getTtr() {
        return ttr;
    }

    public void setTtr(Long ttr) {
        this.ttr = ttr;
    }


    @Override
    public String toString() {
        return "DelayTaskDTO{" +
                "bizType='" + bizType + '\'' +
                ", seqId='" + seqId + '\'' +
                ", topic='" + topic + '\'' +
                ", delay=" + delay +
                ", ttr=" + ttr +
                ", body='" + body + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
