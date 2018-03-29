package com.key.delay.server.biz.center.delay.redis;

import com.key.delay.server.biz.center.delay.DelayQueueComponent;
import com.key.delay.server.biz.center.disconf.DelayTopicConfig;
import com.key.delay.server.biz.center.exception.DelayTaskRuntimeException;
import com.key.delay.server.biz.model.DelayTask;
import com.key.jorigin.redis.RedisComponent;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shuguang on 17/11/17.
 * <p>
 * 任务延迟队列
 */

@Component
public class JedisDelayQueueComponent implements DelayQueueComponent<DelayTask> {


    @Autowired
    RedisComponent redisComponent;

    Logger logger = LoggerFactory.getLogger("swallow");

    private static String split_val = "_";

    @Override
    public Long takeTask() {

        String[] topicList = DelayTopicConfig.getDelayTopicList();
        if (topicList == null || topicList.length <= 0) {
            logger.error("===========takeTask topic conf is empty============");
            return 0l;
        }

        //允许10秒内误差
//        long nowSecond = Calendar.getInstance().getTimeInMillis() / 1000 + 10;
        long nowSecond = Calendar.getInstance().getTimeInMillis() / 1000 + 5;

        int i = 0;
        //遍历完所有的topic 没有数据也必须结束
        while (i < topicList.length) {

            String topic = getDelayQueueTopic(FETCH, topicList);

            List<String> valList = redisComponent.zrange(topic, 0, 0, String.class);

            if (CollectionUtils.isNotEmpty(valList)) {
                String[] val = valList.get(0).split(split_val);

                if (Long.parseLong(val[1]) <= nowSecond) {
                    //时间到了，删除任务，返回任务id
                    redisComponent.zremrange(topic, valList.get(0));
                    return Long.parseLong(val[0]);
                }
            }

            i++;
        }
        //遍历完没有返回则，返回0；
        return 0l;

    }

    @Override
    public List<Long> taskBatchTask() {

        //允许10秒内误差
//        long nowSecond = Calendar.getInstance().getTimeInMillis() / 1000 + 10;

        String[] topicList = DelayTopicConfig.getDelayTopicList();
        if (topicList == null || topicList.length <= 0) {
            logger.error("===========taskBatchTask topic conf is empty============");
            return null;
        }

        long nowSecond = Calendar.getInstance().getTimeInMillis() / 1000 + 5;

        int i = 0;
        while (i < topicList.length) {

            String topic = getDelayQueueTopic(FETCH, topicList);

            List<String> valList = redisComponent.zrangeByScore(topic, 0, nowSecond, String.class);

            if (CollectionUtils.isNotEmpty(valList)) {
                List<Long> taskIdList = new ArrayList<Long>();
                for (String bothVal : valList) {
                    String[] val = bothVal.split(split_val);
                    taskIdList.add(Long.parseLong(val[0]));
                }
                redisComponent.zremrangeByScore(topic, 0, nowSecond);
                return taskIdList;
            }

            i++;
        }
        return null;
    }

    @Override
    public boolean addTask(DelayTask task) {


        String[] topicList = DelayTopicConfig.getDelayTopicList();
        if (topicList == null || topicList.length <= 0) {
            logger.error("===========addTask topic conf is empty============");
            throw new DelayTaskRuntimeException("topic is empty");
        }

        String val = task.getId() + split_val + task.getNextExecTime();

        //分数为执行时间，单位秒
        String topic = getDelayQueueTopic(PUT, topicList);
        Long res = redisComponent.zadd(topic, task.getNextExecTime(), val);
        boolean b = res != null && res > 0;
        if (b) {
            logger.info("addTask ok!,topic:{},member:{}; failNum:{},status:{}",
                    new Object[]{topic, val, (task.getFailNum() == null ? 0 : task.getFailNum()), task.getStatus()});
        }
        return b;
    }

    private static int PUT_QUEUE_SIZE = 0;
    private static int putCurIndex = 0;

    private static int FETCH_QUEUE_SIZE = 0;
    private static int fetchCurIndex = 0;

    private static final int PUT = 1, FETCH = 2;

    //
    private String getDelayQueueTopic(int type, String[] topicList) {

        if (type == PUT) {
            PUT_QUEUE_SIZE = topicList.length;

            int index = putCurIndex;

            putCurIndex = (putCurIndex >= (PUT_QUEUE_SIZE - 1)) ? 0 : putCurIndex + 1;

            return topicList[index];
        } else {
            FETCH_QUEUE_SIZE = topicList.length;

            int index = fetchCurIndex;

            fetchCurIndex = (fetchCurIndex >= (FETCH_QUEUE_SIZE - 1)) ? 0 : fetchCurIndex + 1;

            return topicList[index];
        }
    }

}
