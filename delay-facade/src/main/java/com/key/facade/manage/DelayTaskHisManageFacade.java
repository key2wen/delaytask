package com.key.facade.manage;

import com.key.commons.lang.Result;
import com.key.facade.model.DelayTaskHisDTO;

public interface DelayTaskHisManageFacade {

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	Result<Boolean> updateById(DelayTaskHisDTO updateParam);

	/**
	 * 新增一条记录
	 */
	Result<Boolean> insert(DelayTaskHisDTO swallowDelayTaskHis);
}