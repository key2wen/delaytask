package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayTaskHis;
import com.key.facade.model.DelayTaskHisDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayTaskHisDTOConverter
		extends AbstractObjectConverter<DelayTaskHis, DelayTaskHisDTO> {

	@Override
	protected DelayTaskHisDTO onBuildDto(DelayTaskHis model) {
		DelayTaskHisDTO domain = new DelayTaskHisDTO();
		BeanUtils.copyProperties(model, domain);
		return domain;
	}

	@Override
	protected DelayTaskHis onBuildModel(DelayTaskHisDTO domain) {
		DelayTaskHis model = new DelayTaskHis();
		BeanUtils.copyProperties(domain, model);
		return model;
	}
}
