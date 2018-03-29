package com.key.delay.server.dao;

import com.key.delay.server.dao.model.DelayTaskHisDO;
import com.key.facade.request.DelayTaskHisQuery;

import java.util.List;

public interface DelayTaskHisDAO {

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	List<DelayTaskHisDO> query(DelayTaskHisQuery query);

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
	DelayTaskHisDO findById(Long id);

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	int updateById(DelayTaskHisDO updateParam);

	/**
	 * 新增一条记录
	 */
	int insert(DelayTaskHisDO delayTaskHisDO);

}