package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.converter.DelayFlowDTOConverter;
import com.key.delay.server.biz.service.DelayFlowService;
import com.key.facade.manage.DelayFlowManageFacade;
import com.key.facade.model.DelayFlowDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DelayFlowManageFacadeImpl implements DelayFlowManageFacade {
	private static final Logger logger = LoggerFactory.getLogger(DelayFlowManageFacadeImpl.class);

	@Autowired
	private DelayFlowService delayFlowService;

	@Autowired
	private DelayFlowDTOConverter converter;

	@Override
	public Result<Boolean> updateById(DelayFlowDTO updateParam) {
		try {
			Assert.notNull(updateParam, "待更新参数不能为空");
			Assert.notNull(updateParam.getId(), "待更新记录编号不能为空");

			int updateResult = delayFlowService.updateById(converter.toModel(updateParam));
			if (updateResult > 0) {
				return Result.buildSucc(true);
			} else {
				return Result.buildSucc(false);
			}
		} catch (Exception ex) {
			logger.warn("更记录信息异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

	@Override
	public Result<Boolean> insert(DelayFlowDTO delayFlowDTO) {
		try {
			Assert.notNull(delayFlowDTO, "待新增记录参数不能为空");

			int insertResult = delayFlowService.insert(converter.toModel(delayFlowDTO));
			if (insertResult > 0) {
				return Result.buildSucc(true);
			} else {
				return Result.buildSucc(false);
			}
		} catch (Exception ex) {
			logger.warn("新增记录信息异常", ex);

			return Result.buildFail(ResultCodeConstants.ERROR_CODE, ex.getMessage());
		}
	}

}