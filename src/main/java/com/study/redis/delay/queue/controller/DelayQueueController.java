package com.study.redis.delay.queue.controller;

import com.study.redis.delay.queue.domain.DelayJob;
import com.study.redis.delay.queue.handler.DelayQueueHandler;
import com.study.redis.delay.queue.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delay-queue")
public class DelayQueueController {

    @Autowired
    private DelayQueueHandler delayQueueHandler;

    /**
     * http://localhost:8899/delay-queue/push?topic=test&id=abc123&delay=30&ttr=100&body=test
     * push job to queue
     * @param topic Job类型。可以理解成具体的业务名称
     * @param id Job的唯一标识。用来检索和删除指定的Job信息。通常是 topic + 主键 id
     * @param delay Job需要延迟的时间。单位：秒。（服务端会将其转换为绝对时间）
     * @param ttr Job执行超时时间。单位：秒。
     * @param body Job的内容，供消费者做具体的业务处理，以json格式存储。
     * @return
     */
    @RequestMapping("push")
    public Result push(@RequestParam String topic, @RequestParam String id, @RequestParam long delay,
                         @RequestParam long ttr, @RequestParam String body) {
        DelayJob delayJob = new DelayJob();
        delayJob.setTopic(topic);
        delayJob.setId(id);
        delayJob.setDelay(delay);
        delayJob.setTtr(ttr);
        delayJob.setBody(body);
        delayQueueHandler.push(delayJob);
        return Result.ok(id, "");
    }

    /**
     * http://localhost:8899/delay-queue/pop?topic=test
     * get ready job for particular topic
     * @param topic
     * @return
     */
    @RequestMapping("pop")
    public Result pop(@RequestParam String topic) {
        if (StringUtils.isEmpty(topic)) {
            return Result.error("Parameter topic should not be empty!");
        }
        return delayQueueHandler.pop(topic);
    }

    // TODO Delete job
    @RequestMapping("delete")
    public Result delete() {

        return null;
    }

}
