package com.key.delay.server.biz.converter;

import com.key.commons.util.AbstractObjectConverter;
import com.key.delay.server.biz.model.DelayTaskHis;
import com.key.delay.server.dao.model.DelayTaskHisDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DelayTaskHisDOConverter
		extends AbstractObjectConverter<DelayTaskHis, DelayTaskHisDO> {

	@Override
	protected DelayTaskHisDO onBuildDto(DelayTaskHis model) {
		DelayTaskHisDO domain = new DelayTaskHisDO();
		BeanUtils.copyProperties(model, domain);
		return domain;
	}

	@Override
	protected DelayTaskHis onBuildModel(DelayTaskHisDO domain) {
		DelayTaskHis model = new DelayTaskHis();
		BeanUtils.copyProperties(domain, model);
		return model;
	}
}
