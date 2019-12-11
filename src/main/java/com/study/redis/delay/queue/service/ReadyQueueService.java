package com.study.redis.delay.queue.service;

import com.study.redis.delay.queue.domain.DelayJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadyQueueService {

    @Value("${delay-queue.ready-queue-name}")
    private String readyQueueName;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将 job 加入队列
     * @param jobId
     */
    public void pushJobToReadyQueue(String jobId) {
        // 前提是没有分布式
        synchronized (readyQueueName) {
            redisTemplate.opsForList().leftPush(readyQueueName, jobId);
        }
    }

    /**
     * get all data from ready queue
     */
    public List<String> getJobsFromReadyQueue() {
        synchronized (readyQueueName) {
            List<String> list = redisTemplate.opsForList().range(readyQueueName, 0, -1);
            // TODO 取出所有的 job id
//            redisTemplate.opsForList().rig
            return list;
        }
    }
}
