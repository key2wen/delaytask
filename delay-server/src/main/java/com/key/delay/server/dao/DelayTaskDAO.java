package com.key.delay.server.dao;

import com.key.delay.server.dao.model.DelayTaskDO;
import com.key.facade.request.DelayTaskComQuery;
import com.key.facade.request.DelayTaskQuery;

import java.util.List;

public interface DelayTaskDAO {

	/**
	 * 根据查询参数查询数据
	 *
	 * @param query
	 *            查询参数
	 */
	List<DelayTaskDO> query(DelayTaskQuery query);


	List<DelayTaskDO> queryCom(DelayTaskComQuery query);

	/**
	 * 根据查询参数查询数据总量
	 *
	 * @param query
	 *            查询参数
	 */
	Integer count(DelayTaskQuery query);

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *            数据库ID
	 */
	DelayTaskDO findById(Long id);

	/**
	 * 根据id更新一调数据
	 *
	 * @param updateParam
	 *            更新参数
	 */
	int updateById(DelayTaskDO updateParam);

	/**
	 * 新增一条记录
	 */
	int insert(DelayTaskDO delayTaskDO);

	int findFinishAndInsertToHis(Long nextExecTime);

	int deleteFinish(Long nextExecTime);

}