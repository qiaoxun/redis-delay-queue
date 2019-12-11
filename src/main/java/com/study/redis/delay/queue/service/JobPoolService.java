package com.study.redis.delay.queue.service;

import com.study.redis.delay.queue.domain.DelayJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobPoolService {

    @Value("${delay-queue.job-pool-name}")
    private String jobPoolName;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * push delay job to job pool
     * @param delayJob
     */
    public void pushJobToJobPool(DelayJob delayJob) {
        Map<String, Object> map = new HashMap<>();
        map.put("topic", delayJob.getTopic());
        map.put("id", delayJob.getId());
        map.put("delay", delayJob.getDelay());
        map.put("ttl", delayJob.getTtr());
        map.put("body", delayJob.getBody());
        redisTemplate.opsForHash().put(jobPoolName, delayJob.getId(), map);
    }

    /**
     * delete delay job from job pool
     * @param id
     */
    public void deleteJobFromJobPool(String id) {
        redisTemplate.opsForHash().delete(jobPoolName, id);
    }
}
