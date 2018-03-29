package com.key.delay.server.dao.impl;

import com.key.delay.server.dao.DelayFlowDAO;
import com.key.delay.server.dao.model.DelayFlowDO;
import com.key.facade.request.DelayFlowQuery;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class DelayFlowDAOImpl extends SqlSessionDaoSupport implements DelayFlowDAO {

	@Override
	public List<DelayFlowDO> query(DelayFlowQuery query) {

		Assert.notNull(query);

		return this.getSqlSession().selectList("DelayFlow.QUERY", query);
	}

	@Override
	public Integer count(DelayFlowQuery query) {

		Assert.notNull(query);

		return this.getSqlSession().selectOne("DelayFlow.COUNT", query);
	}

	@Override
	public DelayFlowDO findById(Long id) {

		Assert.notNull(id, "流水id不能为空");

		return this.getSqlSession().selectOne("DelayFlow.FIND_BY_ID", id);
	}

	@Override
	public int updateById(DelayFlowDO updateParam) {

		Assert.notNull(updateParam);
		Assert.notNull(updateParam.getId(), "流水id不能为空");

		updateParam.setCreateTime(null);
		updateParam.setModifyTime(new Date());

		return this.getSqlSession().update("DelayFlow.UPDATE_BY_ID", updateParam);
	}

	@Override
	public int insert(DelayFlowDO delayFlowDO) {

		checkParamForInsert(delayFlowDO);

		delayFlowDO.setCreateTime(new Date());
		delayFlowDO.setModifyTime(null);

		return this.getSqlSession().insert("DelayFlow.INSERT", delayFlowDO);
	}

	private void checkParamForInsert(DelayFlowDO delayFlowDO) {

		Assert.notNull(delayFlowDO);

		// TODO check code

	}

}