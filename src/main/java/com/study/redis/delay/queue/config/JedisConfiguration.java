package com.study.redis.delay.queue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool() {
        RedisProperties.Pool poolProperties = redisProperties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(poolProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(poolProperties.getMaxWait().toMillis());
        jedisPoolConfig.setMaxTotal(poolProperties.getMaxActive());
        jedisPoolConfig.setMinIdle(poolProperties.getMinIdle());
        JedisPool pool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(),100);
        return pool;
    }

}