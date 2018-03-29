package com.key.delay.client.component;

import com.key.commons.lang.Result;
import com.key.facade.manage.DelayTaskSubmitFacade;
import com.key.facade.model.DelayTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/22.
 */

@Component
public class SubmitDelayTaskComponent {

    @Autowired
    DelayTaskSubmitFacade delayTaskSubmitFacade;

    Logger logger = LoggerFactory.getLogger(SubmitDelayTaskComponent.class);

    @Value("${delay.task.tag}")
    private String tag;

    //提交延迟任务
    public boolean submitTask(DelayTaskDTO delayTaskDTO) {
        delayTaskDTO.setTopic(tag);
        Result<Void> submitRes = delayTaskSubmitFacade.submit(delayTaskDTO);
        if (submitRes != null && submitRes.isSuccess()) {
            logger.info("submit_delay_task_suc, tag:{}, delayTask:{}", tag, delayTaskDTO);
            return true;
        }
        logger.error("submit_delay_task_fail, tag:{},delayTask:{},err:{}",
                new Object[]{tag, delayTaskDTO, (submitRes == null ? "facade返回Null" : submitRes.getErrorMsg())});
        return false;
    }

}
