package com.key.delay.server.dao.impl;

import com.key.delay.server.dao.DelayTaskDAO;
import com.key.delay.server.dao.model.DelayTaskDO;
import com.key.facade.request.DelayTaskComQuery;
import com.key.facade.request.DelayTaskQuery;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DelayTaskDAOImpl extends SqlSessionDaoSupport implements DelayTaskDAO {

    @Override
    public List<DelayTaskDO> query(DelayTaskQuery query) {

        Assert.notNull(query);

        query.decorate();

        return this.getSqlSession().selectList("DelayTask.QUERY", query);
    }

    @Override
    public List<DelayTaskDO> queryCom(DelayTaskComQuery query) {

        Assert.notNull(query);

        query.decorate();

        return this.getSqlSession().selectList("DelayTask.COM_QUERY", query);
    }

    @Override
    public Integer count(DelayTaskQuery query) {

        Assert.notNull(query);

        return this.getSqlSession().selectOne("DelayTask.COUNT", query);
    }

    @Override
    public DelayTaskDO findById(Long id) {

        Assert.notNull(id, "任务id不能为空");

        return this.getSqlSession().selectOne("DelayTask.FIND_BY_ID", id);
    }

    @Override
    public int updateById(DelayTaskDO updateParam) {

        Assert.notNull(updateParam);
        Assert.notNull(updateParam.getId(), "任务id不能为空");

        updateParam.setCreateTime(null);
        updateParam.setModifyTime(new Date());

        return this.getSqlSession().update("DelayTask.UPDATE_BY_ID", updateParam);
    }

    @Override
    public int insert(DelayTaskDO delayTaskDO) {

        checkParamForInsert(delayTaskDO);

        delayTaskDO.setModifyTime(null);

        return this.getSqlSession().insert("DelayTask.INSERT", delayTaskDO);
    }


    @Override
    public int findFinishAndInsertToHis(Long nextExecTime) {

        Map<String, Long> map = new HashMap<String, Long>(1, 1f);
        map.put("nextExecTime", nextExecTime);

        return this.getSqlSession().insert("DelayTask.findFinishAndInsertHis", map);
    }

    @Override
    public int deleteFinish(Long nextExecTime) {

        Map<String, Long> map = new HashMap<String, Long>(1, 1f);
        map.put("nextExecTime", nextExecTime);

        return this.getSqlSession().insert("DelayTask.deleteFinish", map);
    }


    private void checkParamForInsert(DelayTaskDO delayTaskDO) {

        Assert.notNull(delayTaskDO);

        Assert.notNull(delayTaskDO.getCreateTime());

        Assert.notNull(delayTaskDO.getDelay());

        Assert.notNull(delayTaskDO.getTopic());

        Assert.notNull(delayTaskDO.getTtr());

        Assert.notNull(delayTaskDO.getNextExecTime());

        // TODO check code

    }

}