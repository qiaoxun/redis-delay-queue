package com.study.redis.delay.queue.handler;

import com.study.redis.delay.queue.domain.DelayJob;
import com.study.redis.delay.queue.service.DelayQueueService;
import com.study.redis.delay.queue.service.JobPoolService;
import com.study.redis.delay.queue.service.ReadyQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TimerHandler {

    @Value("${delay-queue.delay-queue-name}")
    private String delayQueueName;

    @Autowired
    private DelayQueueService delayQueueService;

    @Autowired
    private JobPoolService jobPoolService;

    @Autowired
    private ReadyQueueService readyQueueService;

//    private ExecutorService pool = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void loopDelayQueue() {
        new Thread(() -> {
            try {
                for(;;) {
                    // 1. get current time
                    long currentMilliTime = System.currentTimeMillis();
                    // 2. get all jobs that the delay time less than current time, which means job should be ready now.
                    // Only get 10 jobs each time
                    Set<ZSetOperations.TypedTuple<Object>> readyJobs = delayQueueService.getReadyJobs(currentMilliTime);
                    log.info("time: " + currentMilliTime + ", delayQueueName = " + delayQueueName + ", result size = " + readyJobs.size());

                    if (CollectionUtils.isEmpty(readyJobs)) {
                        sleepOneSecond();
                        continue;
                    }

                    readyJobs.forEach(typedTuple -> {
                        // 3. get job from job pool
                        String jobId = String.valueOf(typedTuple.getValue());
                        Double delayTime = typedTuple.getScore();
                        // 3.1 delete job from delay queue
                        delayQueueService.deleteJobFromDelayQueue(jobId);
                        if (delayTime < currentMilliTime) {
                            // 3.2 push job into ready queue
                            DelayJob delayJob = jobPoolService.getJobById(String.valueOf(typedTuple.getValue()));
                            log.info("delayJob: " + delayJob);
                            if (delayJob != null) {
                                readyQueueService.pushJobToReadyQueue(delayJob.getTopic(), delayJob.getId());
                            }
                        }

                    });
                    sleepOneSecond();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sleepOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
