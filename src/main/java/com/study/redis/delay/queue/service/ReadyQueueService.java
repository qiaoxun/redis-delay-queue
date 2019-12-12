package com.study.redis.delay.queue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadyQueueService {

    @Value("${delay-queue.ready-queue-name}")
    private String readyQueueName;

    private String underLine = "_";

    @Autowired
    private ListOperations<String, Object> listOperations;

    /**
     * 将 job 加入队列
     * @param topic 不同的 topic 放在不同的 queue 里面
     * @param jobId
     */
    public void pushJobToReadyQueue(String topic, String jobId) {
        listOperations.leftPush(getReadyQueueKey(topic), jobId);
    }

    /**
     * get one ready data from ready queue
     */
    public String getJobFromReadyQueue(String topic) {
        return (String) listOperations.rightPop(getReadyQueueKey(topic));
    }

    /**
     * get key
     * @param topic
     * @return
     */
    private String getReadyQueueKey(String topic) {
        return readyQueueName + underLine + topic;
    }
}
