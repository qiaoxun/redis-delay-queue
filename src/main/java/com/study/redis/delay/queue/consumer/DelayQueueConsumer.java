package com.study.redis.delay.queue.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.locks.LockSupport;

@Component
public class DelayQueueConsumer {

    @Value("${delay-queue-name}")
    private String delayQueueName;

//    @Autowired
//    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void consume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    long currentMilliTime = System.currentTimeMillis();
                    Set<String> jobIdSet = redisTemplate.opsForZSet().range(delayQueueName, 0, -1);
//                    Set<String> jobIdSet = redisTemplate.opsForZSet().rangeByScore(delayQueueName, 0, currentMilliTime, 0, 10);
//            Set<String> jobIdSet = jedisPool.getResource().zrangeByScore(delayQueueName, "0", String.valueOf(currentMilliTime), 0, 10);
                    System.out.println("time: " + currentMilliTime + ", delayQueueName = " + delayQueueName + ", result size = " + jobIdSet.size());
                    jobIdSet.forEach(jobId -> {
                        System.out.println("job id is " + jobId);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
