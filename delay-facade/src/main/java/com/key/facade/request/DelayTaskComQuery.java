package com.key.facade.request;

import com.key.commons.model.Query;

import java.io.Serializable;

public class DelayTaskComQuery extends Query implements Serializable {

    public static final int NOT_FINISHED = -1, FINISHED = -2;
    /**
     * 任务编号
     */
    private Long id;

    /**
     * 状态：-1.查未完成的 ， －2 查已完成和已超时的，其它查对应的status
     */
    private Integer status;

    /**
     * 精确到秒
     */
    private Long nextExecTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextExecTime() {
        return nextExecTime;
    }

    public void setNextExecTime(Long nextExecTime) {
        this.nextExecTime = nextExecTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}