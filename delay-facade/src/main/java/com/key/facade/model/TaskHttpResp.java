package com.key.facade.model;

import java.io.Serializable;

/**
 * create by shuguang
 */
public class TaskHttpResp implements Serializable {

    private boolean suc;

    private Long taskId;

    private String errMsg;

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
