package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.converter.DelayFlowDTOConverter;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.facade.model.DelayFlowDTO;
import com.key.facade.query.DelayFlowQueryFacade;
import com.key.facade.request.DelayFlowQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DelayFlowQueryFacadeImpl implements DelayFlowQueryFacade {
	private static final Logger logger = LoggerFactory.getLogger(DelayFlowQueryFacadeImpl.class);

	@Autowired
	private DelayFlowService delayFlowService;

	@Autowired
	private DelayFlowDTOConverter converter;

	@Override
	public Result<DelayFlowDTO> findById(Long id) {
		try {
			Assert.notNull(id, "编号不能为空");

			DelayFlowDTO delayFlowDTO = converter.toDto(delayFlowService.findById(id));
			return Result.buildSucc(delayFlowDTO);
		} catch (Exception ex) {
			logger.warn("根据编号查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<List<DelayFlowDTO>> query(DelayFlowQuery query) {
		try {
			Assert.notNull(query, "查询参数不能为空");

			List<DelayFlowDTO> delayFlowDTOList = converter.asDtoList(delayFlowService.query(query));
			return Result.buildSucc(delayFlowDTOList);
		} catch (Exception ex) {
			logger.warn("根据参数查询记录列表异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<Integer> count(DelayFlowQuery query) {
		try {
			Assert.notNull(query, "统计参数不能为空");

			Integer count = delayFlowService.count(query);
			return Result.buildSucc(count);
		} catch (Exception ex) {
			logger.warn("根据统计参数查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}
}