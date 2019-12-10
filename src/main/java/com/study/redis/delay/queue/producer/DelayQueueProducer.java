package com.study.redis.delay.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
public class DelayQueueProducer {

    @Value("${delay-queue-name}")
    private String delayQueueName;

    @Autowired
    private JedisPool jedisPool;

    public void addDataToQueue(int jobId, long timeout) {
        long endTime = System.currentTimeMillis() + (timeout * 60 * 1000);
        jedisPool.getResource().zadd(delayQueueName, endTime, String.valueOf(jobId));
    }

}
