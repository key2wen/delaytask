package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayFlow;
import com.key.facade.model.DelayFlowDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayFlowDTOConverter extends AbstractObjectConverter<DelayFlow, DelayFlowDTO> {

	@Override
	protected DelayFlowDTO onBuildDto(DelayFlow model) {
		DelayFlowDTO domain = new DelayFlowDTO();
		BeanUtils.copyProperties(model, domain);
		return domain;
	}

	@Override
	protected DelayFlow onBuildModel(DelayFlowDTO domain) {
		DelayFlow model = new DelayFlow();
		BeanUtils.copyProperties(domain, model);
		return model;
	}
}
