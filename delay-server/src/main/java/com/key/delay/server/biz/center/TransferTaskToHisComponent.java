package com.key.delay.server.biz.center;

/**
 * Created by shuguang on 17/11/21.
 */

import com.key.delay.server.biz.service.DelayTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 清除执行成功的任务。。
 */

@Component
public class TransferTaskToHisComponent {


    @Autowired
    DelayTaskService delayTaskService;

    Logger logger = LoggerFactory.getLogger("swallow");


    public TransferTaskToHisComponent() {
    }

    public void transfer() {

        int[] months = {-3, -2, -1};

        for (int month : months) {

            transferMonth(month);
        }
        transferAllTIme();
    }

    public String transferAllTIme() {

        int ins = delayTaskService.findFinishAndInsertToHis(null);

        int del = delayTaskService.deleteFinish(null);

        logger.info("transferTaskToHis, All_Time, insert:{}, del:{}", new Object[]{ins, del});

        return "all_" + ins + "_" + del;
    }

    public String transferMonth(int month) {

        Long execTime = getBeforeMonthSed(month);

        int ins = delayTaskService.findFinishAndInsertToHis(execTime);

        int del = delayTaskService.deleteFinish(execTime);

        logger.info("transferTaskToHis, month:{}, insert:{}, del:{}", new Object[]{month, ins, del});

        return month + "_" + ins + "_" + del;
    }

    public Long getBeforeMonthSed(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() / 1000;
    }

}
