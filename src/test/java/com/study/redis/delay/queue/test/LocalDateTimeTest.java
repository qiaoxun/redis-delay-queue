package com.study.redis.delay.queue.test;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeTest {

    @Test
    public void test() {
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalTime.now().plusMinutes(30).getNano());
        System.out.println(LocalDateTime.now().plusMinutes(30).toLocalTime().toNanoOfDay());
    }

    public void test1() {
        long time = System.currentTimeMillis();
        System.out.println(time);
    }

}
