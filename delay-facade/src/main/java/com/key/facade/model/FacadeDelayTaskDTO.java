package com.key.facade.model;

import java.io.Serializable;
import java.util.Date;

public class FacadeDelayTaskDTO implements Serializable {

    private static final long serialVersionUID = -8982558563377611789L;

    /**
     * 任务编号
     */
    private Long id;

    /**
     * 任务分组code
     */
    private String topic;

    /**
     * 延迟时间
     */
    private Long delay;

    /**
     * 任务执行超时时长
     */
    private Long ttr;

    /**
     * @see com.key.facade.constants.DelayTaskStatusConstants
     */
    private Integer status;

    /**
     * 自定义任务内容json
     */
    private String body;

    /**
     * 精确到秒
     */
    private Long nextExecTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }


    public Integer getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public Long getNextExecTime() {
        return nextExecTime;
    }

    public void setNextExecTime(Long nextExecTime) {
        this.nextExecTime = nextExecTime;
    }
}