package com.study.redis.delay.queue.controller;

import com.study.redis.delay.queue.producer.DelayQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class ProducerController {

    @Autowired
    private DelayQueueProducer delayQueueProducer;

    /**
     *
     * @param jobId
     * @param timeout should be minutes
     * @return
     */
    @GetMapping("addJob")
    public int addJob(@RequestParam int jobId, @RequestParam int timeout) {
        long endTime = System.currentTimeMillis() + (timeout * 60 * 1000);
        delayQueueProducer.addDataToQueue(jobId, endTime);
        return jobId;
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

}
