package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.converter.DelayTaskHisDTOConverter;
import com.key.delay.server.biz.service.DelayTaskHisService;
import com.key.facade.manage.DelayTaskHisManageFacade;
import com.key.facade.model.DelayTaskHisDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DelayTaskHisManageFacadeImpl implements DelayTaskHisManageFacade {
	private static final Logger logger = LoggerFactory.getLogger(DelayTaskHisManageFacadeImpl.class);

	@Autowired
	private DelayTaskHisService delayTaskHisService;

	@Autowired
	private DelayTaskHisDTOConverter converter;

	@Override
	public Result<Boolean> updateById(DelayTaskHisDTO updateParam) {
		try {
			Assert.notNull(updateParam, "待更新参数不能为空");
			Assert.notNull(updateParam.getId(), "待更新记录编号不能为空");

			int updateResult = delayTaskHisService.updateById(converter.toModel(updateParam));
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
	public Result<Boolean> insert(DelayTaskHisDTO delayTaskHisDTO) {
		try {
			Assert.notNull(delayTaskHisDTO, "待新增记录参数不能为空");

			int insertResult = delayTaskHisService.insert(converter.toModel(delayTaskHisDTO));
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