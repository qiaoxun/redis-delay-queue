package com.study.redis.delay.queue.service;

import com.study.redis.delay.queue.domain.DelayJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DelayQueueService {

    @Value("${delay-queue.delay-queue-name}")
    private String delayQueueName;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 将 job 加入延迟队列
     * @param delayJob
     */
    public void pushJobToDelayQueue(DelayJob delayJob) {
        // 计算过期时间
        long endTime = System.currentTimeMillis() + delayJob.getDelay() * 1000;
        zSetOperations.add(delayQueueName, delayJob.getId(), endTime);
    }

    /**
     * get at most 10 ready jobs
     * @param time normally, current time
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getReadyJobs(long time) {
        Set<ZSetOperations.TypedTuple<Object>> readyJobs = zSetOperations.rangeByScoreWithScores(delayQueueName, 0, time, 0, 10);
        return readyJobs;
    }

    /**
     * delete data from delay queue
     * @param id
     */
    public void deleteJobFromDelayQueue(String id) {
        System.out.println(id);
        System.out.println(delayQueueName);
        long result = zSetOperations.remove(id);
        System.out.println(result);
    }

}
