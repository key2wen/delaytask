package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.converter.DelayTaskHisDTOConverter;
import com.key.delay.server.biz.service.DelayTaskHisService;
import com.key.facade.model.DelayTaskHisDTO;
import com.key.facade.query.DelayTaskHisQueryFacade;
import com.key.facade.request.DelayTaskHisQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DelayTaskHisQueryFacadeImpl implements DelayTaskHisQueryFacade {
	private static final Logger logger = LoggerFactory.getLogger(DelayTaskHisQueryFacadeImpl.class);

	@Autowired
	private DelayTaskHisService delayTaskHisService;

	@Autowired
	private DelayTaskHisDTOConverter converter;

	@Override
	public Result<DelayTaskHisDTO> findById(Long id) {
		try {
			Assert.notNull(id, "编号不能为空");

			DelayTaskHisDTO delayTaskHisDTO = converter.toDto(delayTaskHisService.findById(id));
			return Result.buildSucc(delayTaskHisDTO);
		} catch (Exception ex) {
			logger.warn("根据编号查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<List<DelayTaskHisDTO>> query(DelayTaskHisQuery query) {
		try {
			Assert.notNull(query, "查询参数不能为空");

			List<DelayTaskHisDTO> delayTaskHisDTOList = converter
					.asDtoList(delayTaskHisService.query(query));
			return Result.buildSucc(delayTaskHisDTOList);
		} catch (Exception ex) {
			logger.warn("根据参数查询记录列表异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<Integer> count(DelayTaskHisQuery query) {
		try {
			Assert.notNull(query, "统计参数不能为空");

			Integer count = delayTaskHisService.count(query);
			return Result.buildSucc(count);
		} catch (Exception ex) {
			logger.warn("根据统计参数查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}
}