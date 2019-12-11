package com.study.redis.delay.queue.controller;

import com.study.redis.delay.queue.producer.DelayQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delay-queue")
public class DelayQueueController {

    @Autowired
    private DelayQueueProducer delayQueueProducer;

    /**
     * push job to queue
     * @param topic Job类型。可以理解成具体的业务名称
     * @param id Job的唯一标识。用来检索和删除指定的Job信息。通常是 topic + 主键 id
     * @param delay Job需要延迟的时间。单位：秒。（服务端会将其转换为绝对时间）
     * @param ttr Job执行超时时间。单位：秒。
     * @param body Job的内容，供消费者做具体的业务处理，以json格式存储。
     * @return
     */
    @GetMapping("push")
    public String addJob(@RequestParam String topic, @RequestParam String id, @RequestParam long delay,
                         @RequestParam long ttr, @RequestParam String body) {
        // TODO
//        delayQueueProducer.addDataToQueue(id, timeout);
        return id;
    }

}
