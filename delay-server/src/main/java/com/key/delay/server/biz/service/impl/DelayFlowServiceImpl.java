package com.key.delay.server.biz.service.impl;

import com.key.delay.server.biz.converter.DelayFlowDOConverter;
import com.key.delay.server.biz.model.DelayFlow;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.delay.server.dao.DelayFlowDAO;
import com.key.delay.server.dao.model.DelayFlowDO;
import com.key.facade.request.DelayFlowQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayFlowServiceImpl implements DelayFlowService {

    @Autowired
    private DelayFlowDAO delayFlowDAO;

    @Autowired
    private DelayFlowDOConverter converter;

    @Override
    public List<DelayFlow> query(DelayFlowQuery query) {
        return converter.asModelList(delayFlowDAO.query(query));
    }

    @Override
    public Integer count(DelayFlowQuery query) {
        return delayFlowDAO.count(query);
    }

    @Override
    public DelayFlow findById(Long id) {
        return converter.toModel(delayFlowDAO.findById(id));
    }

    @Override
    public int updateById(DelayFlow updateParam) {
        return delayFlowDAO.updateById(converter.toDto(updateParam));
    }

    @Override
    public int insert(DelayFlow delayFlow) {

        DelayFlowDO delayFlowDO = converter.toDto(delayFlow);

        int num = delayFlowDAO.insert(delayFlowDO);

        if (num > 0) {
            delayFlow.setId(delayFlowDO.getId());
        }

        return num;
    }

}