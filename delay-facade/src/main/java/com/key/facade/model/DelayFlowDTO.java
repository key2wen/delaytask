package com.key.facade.model;

import java.io.Serializable;
import java.util.Date;

public class DelayFlowDTO implements Serializable {

	private static final long serialVersionUID = -8595595163629738378L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 业务订单号:通过业务类型和工具生成
	 */
	private String seqId;

	/**
	 * 消息任务编号
	 */
	private Long taskId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public String getBizType() {
		return bizType;
	}

	public String getSeqId() {
		return seqId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}