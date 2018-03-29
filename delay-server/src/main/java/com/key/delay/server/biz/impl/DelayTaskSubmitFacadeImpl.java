package com.key.delay.server.biz.impl;

import com.key.commons.lang.Result;
import com.key.commons.lang.constants.ResultCodeConstants;
import com.key.delay.server.biz.service.DelayTaskManageService;
import com.key.facade.manage.DelayTaskSubmitFacade;
import com.key.facade.model.DelayTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DelayTaskSubmitFacadeImpl implements DelayTaskSubmitFacade {

    private static final Logger logger = LoggerFactory.getLogger(DelayTaskSubmitFacadeImpl.class);

    @Autowired
    private DelayTaskManageService delayTaskManageService;


    @Override
    public Result<Void> submit(DelayTaskDTO delayTaskDTO) {
        try {

            Assert.notNull(delayTaskDTO, "参数为空");

            if (delayTaskManageService.submitTask(delayTaskDTO) > 0) {
                return Result.buildSucc(null);
            }

        } catch (Exception e) {
            logger.error("提交延迟任务异常,{}", e.getMessage(), e);
            return Result.buildFail(ResultCodeConstants.ERROR_CODE, e.getMessage());
        }
        return Result.buildFail(ResultCodeConstants.ERROR_CODE, "提交失败咯");
    }
}