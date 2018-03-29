package com.key.facade.query;


import com.key.commons.lang.Result;
import com.key.facade.model.FacadeDelayTaskDTO;
import com.key.facade.request.DelayTaskQuery;

import java.util.List;

public interface DelayTaskQueryFacade {

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	Result<FacadeDelayTaskDTO> findById(Long id);

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	Result<List<FacadeDelayTaskDTO>> query(DelayTaskQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Result<Integer> count(DelayTaskQuery query);

}