package com.key.delay.server.biz.service;

import com.key.facade.model.DelayTaskDTO;

public interface DelayTaskManageService {

	/**
	 * 提交新的延迟任务
	 * 
	 * @param task
	 */
	long submitTask(DelayTaskDTO task);

}
