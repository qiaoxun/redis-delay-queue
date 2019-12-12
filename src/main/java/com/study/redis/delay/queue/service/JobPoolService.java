package com.study.redis.delay.queue.service;

import com.study.redis.delay.queue.domain.DelayJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

@Component
public class JobPoolService {

    @Value("${delay-queue.job-pool-name}")
    private String jobPoolName;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    /**
     * push delay job to job pool
     * @param delayJob
     */
    public void pushJobToJobPool(DelayJob delayJob) {
        hashOperations.put(jobPoolName, delayJob.getId(), delayJob);
    }

    /**
     * get delay job by id
     * @param jobId
     * @return
     */
    public DelayJob getJobById(String jobId) {
        return (DelayJob) hashOperations.get(jobPoolName, jobId);
    }

    /**
     * delete delay job from job pool
     * @param jobId
     */
    public void deleteJobFromJobPool(String jobId) {
        hashOperations.delete(jobPoolName, jobId);
    }
}
