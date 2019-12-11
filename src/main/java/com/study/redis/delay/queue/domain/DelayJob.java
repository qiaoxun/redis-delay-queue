package com.study.redis.delay.queue.domain;

import lombok.Data;

@Data
public class DelayJob {
    /**
     * Job类型。可以理解成具体的业务名称。
     */
    private String topic;

    /**
     * Job的唯一标识。用来检索和删除指定的Job信息。通常是 topic + 主键 id
     */
    private String id;

    /**
     * Job需要延迟的时间。单位：秒。（服务端会将其转换为绝对时间）
     */
    private long delay;

    /**
     * （time-to-run)：Job执行超时时间。单位：秒。
     */
    private long ttr;

    /**
     * Job的内容，供消费者做具体的业务处理，以json格式存储。
     */
    private String body;
}
