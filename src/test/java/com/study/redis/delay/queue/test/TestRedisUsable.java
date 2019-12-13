package com.study.redis.delay.queue.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRedisUsable {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

//    @Test
//    public void test() {
//        System.out.println(jedisPool.getResource().ping("ping pong"));
//    }

//    @Test
//    public void zsetTest() {
//        Set<String> results = jedisPool.getResource().zrangeByScore("delay_queue", "0", "1575908771213", 0, 2);
//        results.forEach(System.out::println);
//    }

    @Test
    public void testRestTemplate() {
        redisTemplate.opsForValue().setIfAbsent("key", "testest");
        System.out.println(redisTemplate.opsForValue().get("key"));
    }

    @Test
    public void testRestTemplate1() {
        redisTemplate.opsForZSet().add("test", "zhangsan", 100);
        Object value = redisTemplate.opsForZSet().range("test", 0, 1);
        System.out.println(value);
//        System.out.println(set.size());
//        System.out.println(set);
    }

    @Test
    public void test2() {
//        redisTemplate.opsForZSet().rangeByScoreWithScores("delay_queue", 0, 1000000000, 0, 10);
//        zSetOperations.getOperations().boundZSetOps()
        zSetOperations.rangeByScore("delay_queue", 0, 1000000000, 0, 10);
    }
}
