package com.study.redis.delay.queue.service;

import com.study.redis.delay.queue.domain.DelayJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueService {

    @Value("${delay-queue.delay-queue-name}")
    private String delayQueueName;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将 job 加入延迟队列
     * @param delayJob
     */
    public void pushJobToDelayQueue(DelayJob delayJob) {
        // 计算过期时间
        long endTime = System.currentTimeMillis() + delayJob.getDelay() * 1000;
        redisTemplate.opsForZSet().add(delayQueueName, delayJob.getId(), endTime);
    }

    /**
     * delete data from delay queue
     * @param id
     */
    public void deleteJobFromDelayQueue(String id) {
        redisTemplate.opsForZSet().remove(delayQueueName, id);
    }

}
