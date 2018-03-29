package com.key.delay.server.biz.service;

import com.key.delay.server.biz.model.DelayFlow;
import com.key.facade.request.DelayFlowQuery;

import java.util.List;

public interface DelayFlowService {

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	List<DelayFlow> query(DelayFlowQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Integer count(DelayFlowQuery query);

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	DelayFlow findById(Long id);

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	int updateById(DelayFlow updateParam);

	/**
	 * 新增一条记录
	 */
	int insert(DelayFlow delayFlow);
}