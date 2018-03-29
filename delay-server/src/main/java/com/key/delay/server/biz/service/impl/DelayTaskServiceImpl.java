package com.key.delay.server.biz.service.impl;

import com.key.delay.server.biz.converter.DelayTaskDOConverter;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.delay.server.dao.DelayTaskDAO;
import com.key.delay.server.dao.model.DelayTaskDO;
import com.key.facade.request.DelayTaskComQuery;
import com.key.facade.request.DelayTaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayTaskServiceImpl implements DelayTaskService {

    @Autowired
    private DelayTaskDAO delayTaskDAO;

    @Autowired
    private DelayTaskDOConverter converter;

    @Override
    public List<DelayTask> query(DelayTaskQuery query) {
        return converter.asModelList(delayTaskDAO.query(query));
    }

    @Override
    public List<DelayTask> queryCom(DelayTaskComQuery query) {
        return converter.asModelList(delayTaskDAO.queryCom(query));
    }

    @Override
    public Integer count(DelayTaskQuery query) {
        return delayTaskDAO.count(query);
    }

    @Override
    public DelayTask findById(Long id) {
        return converter.toModel(delayTaskDAO.findById(id));
    }

    @Override
    public int updateById(DelayTask updateParam) {
        return delayTaskDAO.updateById(converter.toDto(updateParam));
    }

    @Override
    public int insert(DelayTask delayTask) {

        DelayTaskDO delayTaskDO = converter.toDto(delayTask);

        int num = delayTaskDAO.insert(delayTaskDO);

        if (num > 0) {
            delayTask.setId(delayTaskDO.getId());
            delayTask.setCreateTime(delayTaskDO.getCreateTime());
            return num;
        }

        return 0;
    }

    @Override
    public int findFinishAndInsertToHis(Long nextExecTime) {

        return delayTaskDAO.findFinishAndInsertToHis(nextExecTime);
    }

    @Override
    public int deleteFinish(Long nextExecTime) {

        return delayTaskDAO.deleteFinish(nextExecTime);
    }

}