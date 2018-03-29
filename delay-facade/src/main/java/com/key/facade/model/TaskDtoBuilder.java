package com.key.facade.model;

import java.io.Serializable;

public class TaskDtoBuilder implements Serializable {


    private DelayTaskDTO taskDTO;

    public DelayTaskDTO toTaskDTO() {
        return taskDTO;
    }

    /**
     * 设置任务必填的信息， 相同的bizType 和 seqId 提交会被拒绝
     *
     * @param bizType 必填参数 业务类型
     * @param seqId   必填参数  业务序列号
     * @param delay   必填参数 单位秒 延迟执行时间
     * @param ttr     必填参数 单位秒 第一次延迟时间到后执行失败，再次执行的时间间隔
     * @return
     */
    public static TaskDtoBuilder build(String bizType, String seqId, Long delay, Long ttr) {

        TaskDtoBuilder builder = new TaskDtoBuilder();

        if (builder.taskDTO == null) {
            builder.taskDTO = new DelayTaskDTO();
        }

        builder.taskDTO.setBizType(bizType);
        builder.taskDTO.setSeqId(seqId);
        builder.taskDTO.setDelay(delay);
        builder.taskDTO.setTtr(ttr);

        return builder;
    }

    /**
     * 业务自定义业务内容
     *
     * @param body
     * @return
     */
    public TaskDtoBuilder buildBody(String body) {
        if (this.taskDTO == null) {
            this.taskDTO = new DelayTaskDTO();
        }
        this.taskDTO.setBody(body);
        return this;
    }

    public TaskDtoBuilder buildRemark(String remark) {
        if (this.taskDTO == null) {
            this.taskDTO = new DelayTaskDTO();
        }
        this.taskDTO.setRemark(remark);
        return this;
    }

}
