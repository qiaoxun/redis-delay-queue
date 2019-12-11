package com.study.redis.delay.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueProducer {

    @Value("${delay-queue.delay-queue-name}")
    private String delayQueueName;

    @Autowired
    private RedisTemplate redisTemplate;

    public void addDataToQueue(int jobId, long timeout) {
        long endTime = System.currentTimeMillis() + (timeout * 60 * 1000);
        redisTemplate.opsForZSet().add(delayQueueName, jobId, endTime);
    }

}
