package com.key.delay.server.biz.center.delay.redis;

import com.key.delay.server.biz.center.delay.ExecuteQueueComponent;
import com.key.jorigin.redis.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shuguang on 17/11/17.
 * <p>
 * 可执行的任务队列,准备执行
 */

@Component
public class JedisExecuteQueueComponent implements ExecuteQueueComponent<Long> {

    @Autowired
    RedisComponent redisComponent;

    private final static String queue_name = "dk_swallow_redis_exec_queue";

    /**
     * @param taskId
     * @return
     */
    @Override
    public boolean addExecuteTask(Long taskId) {
        Long res = redisComponent.lpush(queue_name, taskId);
        return res != null && res > 0;
    }

    @Override
    public Long takeExecuteTask() {
        return redisComponent.rpop(queue_name, Long.class);
    }

}
