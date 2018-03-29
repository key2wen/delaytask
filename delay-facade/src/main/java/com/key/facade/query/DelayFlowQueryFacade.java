package com.key.facade.query;


import com.key.commons.lang.Result;
import com.key.facade.model.DelayFlowDTO;
import com.key.facade.request.DelayFlowQuery;

import java.util.List;

public interface DelayFlowQueryFacade {

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	Result<DelayFlowDTO> findById(Long id);

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	Result<List<DelayFlowDTO>> query(DelayFlowQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Result<Integer> count(DelayFlowQuery query);

}