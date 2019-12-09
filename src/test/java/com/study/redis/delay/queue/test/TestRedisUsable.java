package com.study.redis.delay.queue.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRedisUsable {
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void test() {
        System.out.println(jedisPool.getResource().ping("ping pong"));
    }

    @Test
    public void zsetTest() {
        Set<String> results = jedisPool.getResource().zrangeByScore("delay", "0", "1575817600000", 0, 2);
        results.forEach(System.out::println);
    }
}
