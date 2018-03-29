package com.key.delay.server.dao.impl;

import com.key.delay.server.dao.DelayTaskHisDAO;
import com.key.delay.server.dao.model.DelayTaskHisDO;
import com.key.facade.request.DelayTaskHisQuery;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class DelayTaskHisDAOImpl extends SqlSessionDaoSupport implements DelayTaskHisDAO {

    @Override
    public List<DelayTaskHisDO> query(DelayTaskHisQuery query) {

        Assert.notNull(query);

        query.decorate();

        return this.getSqlSession().selectList("DelayTaskHis.QUERY", query);
    }

    @Override
    public Integer count(DelayTaskHisQuery query) {

        Assert.notNull(query);

        return this.getSqlSession().selectOne("DelayTaskHis.COUNT", query);
    }

    @Override
    public DelayTaskHisDO findById(Long id) {

        Assert.notNull(id, "历史任务id不能为空");

        return this.getSqlSession().selectOne("DelayTaskHis.FIND_BY_ID", id);
    }

    @Override
    public int updateById(DelayTaskHisDO updateParam) {

        Assert.notNull(updateParam);
        Assert.notNull(updateParam.getId(), "历史任务id不能为空");

        updateParam.setCreateTime(null);
        updateParam.setModifyTime(new Date());

        return this.getSqlSession().update("DelayTaskHis.UPDATE_BY_ID", updateParam);
    }

    @Override
    public int insert(DelayTaskHisDO delayTaskHisDO) {

        checkParamForInsert(delayTaskHisDO);

        delayTaskHisDO.setCreateTime(new Date());
        delayTaskHisDO.setModifyTime(null);

        return this.getSqlSession().insert("DelayTaskHis.INSERT",
                delayTaskHisDO);
    }

    private void checkParamForInsert(DelayTaskHisDO delayTaskHisDO) {

        Assert.notNull(delayTaskHisDO);

        // TODO check code

    }

}