package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.dao.model.DelayTaskDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayTaskDOConverter extends AbstractObjectConverter<DelayTask, DelayTaskDO> {

    @Override
    protected DelayTaskDO onBuildDto(DelayTask model) {
        DelayTaskDO domain = new DelayTaskDO();
        BeanUtils.copyProperties(model, domain);
        return domain;
    }

    @Override
    protected DelayTask onBuildModel(DelayTaskDO domain) {
        DelayTask model = new DelayTask();
        BeanUtils.copyProperties(domain, model);
        return model;
    }
}
