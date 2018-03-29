package com.key.delay.server.biz.core;

import com.key.delay.server.biz.model.DelayFlow;
import com.key.delay.server.biz.model.DelayTask;

public interface TaskManageCoreService {

	/**
	 * 新增待延迟任务记录
	 */
	public void addNewDelayTask(DelayFlow delayFlow, DelayTask delayTask);

}
