package com.key.delay.business.component;

import com.key.delay.client.component.SubmitDelayTaskComponent;
import com.key.facade.model.DelayTaskDTO;
import com.key.facade.model.TaskDtoBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shuguang on 17/11/20.
 * <p>
 * 延迟队列使用例子：提交延迟消息
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/delay-business-context.xml")
public class DelayTaskTest {

    @Autowired
    SubmitDelayTaskComponent submitDelayTaskComponent;

    //提交延迟任务

    @Test
    public void submitTask() throws InterruptedException {

        //必填参数：
        String bizType = "dk_mk";
        String seqId = "dsgsgkjlljaslfjasdgaslgjaslkdgj";
        Long delay = 3 * 60l;
        Long ttr = 2 * 60l;

        //选填参数：
        String body = "xxxasdfasdf";
        String remark = "sxxxx";
        DelayTaskDTO taskDTO = TaskDtoBuilder.build(bizType, seqId, delay, ttr)
                .buildBody(body).buildRemark(remark).toTaskDTO();

        boolean submitRes = submitDelayTaskComponent.submitTask(taskDTO);
        if (submitRes) {
            //代表提交成功
            //todo 业务自己的逻辑。。。
        } else {
            //todo 处理提交失败的情况。。。
        }
//        CountDownLatch c = new CountDownLatch(1);
//        c.await();
    }

    //执行任务
    public void execTask() {

        /**
         * @see DelayTaskExecExampleListener
         * or
         * @see DelayTaskExecDefaultListener
         */

    }


    /**
     *
     *
     *  1. 引入pom:
     *
     * <dependency>
     *<groupId>com.key</groupId>
     *<artifactId>delay-client</artifactId>
     *<version>1.0</version>
     *</dependency>
     *
     *
     * 2. 需要添加的配置：
     *
     * 2.1 在项目中spring-context.xml中添加引用：
     * <import resource="classpath:META-INF/spring/dk-swallow-client-context.xml"/>
     *
     * 2.2. 添加基本配置信息， 如： delaytask.properties
     * delay.task.tag=delayTaskTag
     * delay.task.execBizMsgConsumer=delayTaskExecQueueListener
     *
     * 2.3. delayTaskExecQueueListener 的boolean 对应的配置，如：delay-business-mq.xml
     *
     *
     * 3. 接口：
     * 3.1 提交延迟任务： delayTaskSubmitFacade.submit(delayTaskDto);
     *     参考例子，submitTask()方法
     * 3.2 自定义任务执行消费
     *    @See DelayTaskExecExampleListener
     *
     *
     */

}
