package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.converter.DelayTaskDTOConverter;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.facade.model.FacadeDelayTaskDTO;
import com.key.facade.query.DelayTaskQueryFacade;
import com.key.facade.request.DelayTaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DelayTaskQueryFacadeImpl implements DelayTaskQueryFacade {
	private static final Logger logger = LoggerFactory.getLogger(DelayTaskQueryFacadeImpl.class);

	@Autowired
	private DelayTaskService delayTaskService;

	@Autowired
	private DelayTaskDTOConverter converter;

	@Override
	public Result<FacadeDelayTaskDTO> findById(Long id) {
		try {
			Assert.notNull(id, "编号不能为空");

			FacadeDelayTaskDTO facadeDelayTaskDTO = converter.toDto(delayTaskService.findById(id));
			return Result.buildSucc(facadeDelayTaskDTO);
		} catch (Exception ex) {
			logger.warn("根据编号查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<List<FacadeDelayTaskDTO>> query(DelayTaskQuery query) {
		try {
			Assert.notNull(query, "查询参数不能为空");

			List<FacadeDelayTaskDTO> facadeDelayTaskDTOList = converter
					.asDtoList(delayTaskService.query(query));
			return Result.buildSucc(facadeDelayTaskDTOList);
		} catch (Exception ex) {
			logger.warn("根据参数查询记录列表异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<Integer> count(DelayTaskQuery query) {
		try {
			Assert.notNull(query, "统计参数不能为空");

			Integer count = delayTaskService.count(query);
			return Result.buildSucc(count);
		} catch (Exception ex) {
			logger.warn("根据统计参数查询记录异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}
}