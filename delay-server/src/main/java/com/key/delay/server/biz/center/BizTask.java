package com.key.delay.server.biz.center;


import com.alibaba.fastjson.JSON;
import com.key.commons.util.HttpClientUtils;
import com.key.delay.server.biz.model.DelayTask;
import com.key.delay.server.biz.service.DelayTaskService;
import com.key.facade.constants.DelayTaskStatusConstants;
import com.key.facade.model.TaskHttpReq;
import com.key.facade.model.TaskHttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/18.
 */
@Component
public class BizTask {


    @Autowired
    private DelayTaskService delayTaskService;

    public boolean execute(Long taskId) {

        DelayTask task = delayTaskService.findById(taskId);
        if (task == null) {
            return true;
        }

        try {
            TaskHttpResp response = invokeTaskBiz(task);
            if (response.isSuc()) {
                DelayTask updateTask = new DelayTask();
                updateTask.setId(taskId);
                updateTask.setStatus(DelayTaskStatusConstants.FINISH);
                if (delayTaskService.updateById(updateTask) > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            //log....mess...
        }
        return false;
    }


    private static final Integer TIME_OUT = 3 * 1000;
    private static final String CHARSET = "utf-8", mimeType = "application/json";

    private TaskHttpResp invokeTaskBiz(DelayTask task) throws Exception {


        TaskHttpReq taskHttpReq = new TaskHttpReq();
        //todo req info
        String url = null;

        String reqBody = JSON.toJSONString(taskHttpReq);
        String resBody = HttpClientUtils.post(url, reqBody, mimeType, CHARSET, TIME_OUT, TIME_OUT);

        TaskHttpResp response = JSON.parseObject(resBody, TaskHttpResp.class);

        return response;
    }

}
