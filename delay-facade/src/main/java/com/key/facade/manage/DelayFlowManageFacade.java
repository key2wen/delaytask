package com.key.facade.manage;

import com.key.commons.lang.Result;
import com.key.facade.model.DelayFlowDTO;

public interface DelayFlowManageFacade {

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	Result<Boolean> updateById(DelayFlowDTO updateParam);

	/**
	 * 新增一条记录
	 */
	Result<Boolean> insert(DelayFlowDTO swallowFlow);
}