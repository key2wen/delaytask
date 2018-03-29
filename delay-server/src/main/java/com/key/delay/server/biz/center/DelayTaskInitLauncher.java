package com.key.delay.server.biz.center;

import com.key.delay.server.biz.center.delay.mq.MqExecuteQueueComponent;
import com.key.delay.server.biz.center.delay.redis.JedisDelayQueueComponent;
import com.key.delay.server.biz.center.execute.FetchTask2DelayQueueThread;
import com.key.delay.server.biz.center.execute.Task2ExecuteQueueThread;
import com.key.delay.server.biz.center.message.MessageComponent;
import com.key.delay.server.biz.service.DelayTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shuguang on 17/11/17.
 */
@Component
public class DelayTaskInitLauncher implements InitializingBean {

    private final ScheduledExecutorService delayTaskSchedExecutor = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService task2ExecSchedExecutor = Executors.newScheduledThreadPool(1);

//    private final ExecutorService task2ExecuteExecutor = Executors.newFixedThreadPool(1);
//    private final ExecutorService executeBizExecutor = Executors.newFixedThreadPool(1);

    private static Logger logger = LoggerFactory.getLogger("swallow");

    @Autowired
    private DelayTaskService swDelayTaskService;
    @Autowired
    private JedisDelayQueueComponent jDelayQueComponent;
    //    @Autowired
//    private JedisExecuteQueueComponent jExeQueComponent;
    @Autowired
    private MqExecuteQueueComponent mqExecQueueComponent;

    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    TransferTaskToHisComponent transferTaskToHisComponent;

    @Autowired
    BizTask bizTask;

    @Override
    public void afterPropertiesSet() throws Exception {

        transferTaskToHisComponent.transfer();

        final FetchTask2DelayQueueThread task2DelayQThread = new FetchTask2DelayQueueThread(swDelayTaskService,
                jDelayQueComponent, messageComponent, false);
        task2DelayQThread.run();

        logger.info("====>schedule task2DelayQ Starting...");
        delayTaskSchedExecutor.scheduleWithFixedDelay(task2DelayQThread, 0l, 10l, TimeUnit.SECONDS);

        Task2ExecuteQueueThread task2ExecThread = new Task2ExecuteQueueThread(swDelayTaskService, jDelayQueComponent, mqExecQueueComponent);
//        task2ExecuteExecutor.submit(task2ExecThread);
        logger.info("====>schedule task2Exec Starting...");
        task2ExecSchedExecutor.scheduleWithFixedDelay(task2ExecThread, 0l, 3l, TimeUnit.SECONDS);

//        executeBizExecutor.submit(new ExecutingTaskThread(bizTask, mqExecQueueComponent));


        //..............

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                delayTaskSchedExecutor.shutdownNow();
                logger.info("====>schedule task2DelayQ End...");
                task2ExecSchedExecutor.shutdownNow();
                logger.info("====>schedule task2Exec End...");
//                task2ExecSchedExecutor.awaitTermination(1, TimeUnit.SECONDS);
//                task2ExecuteExecutor.shutdown();
//                executeBizExecutor.shutdownNow();
            }
        });
    }


}