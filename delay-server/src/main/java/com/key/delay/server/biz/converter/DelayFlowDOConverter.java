package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayFlow;
import com.key.delay.server.dao.model.DelayFlowDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayFlowDOConverter extends AbstractObjectConverter<DelayFlow, DelayFlowDO> {

    @Override
    protected DelayFlowDO onBuildDto(DelayFlow model) {
        DelayFlowDO domain = new DelayFlowDO();
        BeanUtils.copyProperties(model, domain);
        return domain;
    }

    @Override
    protected DelayFlow onBuildModel(DelayFlowDO domain) {
        DelayFlow model = new DelayFlow();
        BeanUtils.copyProperties(domain, model);
        return model;
    }
}
