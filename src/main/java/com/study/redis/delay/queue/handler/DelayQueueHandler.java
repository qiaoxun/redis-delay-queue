package com.study.redis.delay.queue.handler;

import com.study.redis.delay.queue.domain.DelayJob;
import com.study.redis.delay.queue.service.DelayQueueService;
import com.study.redis.delay.queue.service.JobPoolService;
import com.study.redis.delay.queue.service.ReadyQueueService;
import com.study.redis.delay.queue.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class DelayQueueHandler {

    @Autowired
    private DelayQueueService delayQueueService;

    @Autowired
    private JobPoolService jobPoolService;

    @Autowired
    private ReadyQueueService readyQueueService;

    /**
     * 添加延迟任务
     * 1. 先把任务放入 job pool 中
     * 2. 再将任务放入 delay queue
     * @param delayJob
     */
    public void push(DelayJob delayJob) {
        // 1. push job to job pool
        jobPoolService.pushJobToJobPool(delayJob);
        // 2. push job to delay queue
        delayQueueService.pushJobToDelayQueue(delayJob);
    }

    /**
     * pop ready job from job pool
     * @param topic
     * @return
     */
    public Result pop(String topic) {
        String jobId = readyQueueService.getJobFromReadyQueue(topic);
        if (StringUtils.isEmpty(jobId)) {
            return Result.error("No ready job for now, please try again later!");
        }
        DelayJob delayJob = jobPoolService.getJobById(jobId);
        if (delayJob != null) {
            return Result.ok(jobId, delayJob.getBody());
        } else {
            return Result.error("Can't find job with id " + jobId);
        }
    }

}
