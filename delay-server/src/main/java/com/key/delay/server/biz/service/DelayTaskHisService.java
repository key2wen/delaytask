package com.key.delay.server.biz.service;

import com.key.delay.server.biz.model.DelayTaskHis;
import com.key.facade.request.DelayTaskHisQuery;

import java.util.List;

public interface DelayTaskHisService {

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	List<DelayTaskHis> query(DelayTaskHisQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Integer count(DelayTaskHisQuery query);

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	DelayTaskHis findById(Long id);

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	int updateById(DelayTaskHis updateParam);

	/**
	 * 新增一条记录
	 */
	int insert(DelayTaskHis delayTaskHis);
}