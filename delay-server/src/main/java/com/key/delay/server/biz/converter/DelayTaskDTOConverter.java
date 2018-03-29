package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayTask;
import com.key.facade.model.FacadeDelayTaskDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayTaskDTOConverter extends AbstractObjectConverter<DelayTask, FacadeDelayTaskDTO> {

	@Override
	protected FacadeDelayTaskDTO onBuildDto(DelayTask model) {
		FacadeDelayTaskDTO domain = new FacadeDelayTaskDTO();
		BeanUtils.copyProperties(model, domain);
		return domain;
	}

	@Override
	protected DelayTask onBuildModel(FacadeDelayTaskDTO domain) {
		DelayTask model = new DelayTask();
		BeanUtils.copyProperties(domain, model);
		return model;
	}
}
