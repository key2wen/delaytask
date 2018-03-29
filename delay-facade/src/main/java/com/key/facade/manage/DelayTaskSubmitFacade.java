package com.key.facade.manage;

import com.key.commons.lang.Result;
import com.key.facade.model.DelayTaskDTO;

public interface DelayTaskSubmitFacade {

	/**
	 * 新增一条记录
	 */
	Result<Void> submit(DelayTaskDTO delayTaskDTO);
}