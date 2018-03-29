package com.key.delay.server.biz.service.impl;

import com.key.delay.server.biz.converter.DelayTaskHisDOConverter;
import com.key.delay.server.biz.model.DelayTaskHis;
import com.key.delay.server.biz.service.DelayTaskHisService;
import com.key.delay.server.dao.DelayTaskHisDAO;
import com.key.delay.server.dao.model.DelayTaskHisDO;
import com.key.facade.request.DelayTaskHisQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayTaskHisServiceImpl implements DelayTaskHisService {

	@Autowired
	private DelayTaskHisDAO delayTaskHisDAO;

	@Autowired
	private DelayTaskHisDOConverter converter;

	@Override
	public List<DelayTaskHis> query(DelayTaskHisQuery query) {
		return converter.asModelList(delayTaskHisDAO.query(query));
	}

	@Override
	public Integer count(DelayTaskHisQuery query) {
		return delayTaskHisDAO.count(query);
	}

	@Override
	public DelayTaskHis findById(Long id) {
		return converter.toModel(delayTaskHisDAO.findById(id));
	}

	@Override
	public int updateById(DelayTaskHis updateParam) {
		return delayTaskHisDAO.updateById(converter.toDto(updateParam));
	}

	@Override
	public int insert(DelayTaskHis delayTaskHis) {

		DelayTaskHisDO delayTaskHisDO = converter.toDto(delayTaskHis);

		int num = delayTaskHisDAO.insert(delayTaskHisDO);

		if (num > 0) {
			delayTaskHis.setId(delayTaskHisDO.getId());
		}

		return num;
	}

}