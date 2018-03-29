package com.key.facade.query;


import com.key.commons.lang.Result;
import com.key.facade.model.DelayTaskHisDTO;
import com.key.facade.request.DelayTaskHisQuery;

import java.util.List;

public interface DelayTaskHisQueryFacade {

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	Result<DelayTaskHisDTO> findById(Long id);

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	Result<List<DelayTaskHisDTO>> query(DelayTaskHisQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Result<Integer> count(DelayTaskHisQuery query);

}