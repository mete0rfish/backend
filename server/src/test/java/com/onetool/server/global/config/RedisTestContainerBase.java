package com.onetool.server.global.config;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestComponent
public class RedisTestContainerBase {

    @Container
    public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.0.8-alpine")
            .withExposedPorts(6379);

    @Bean("testRedisConnectionFactory")
    public RedisConnectionFactory testRedisConnectionFactory() {
        return new LettuceConnectionFactory(redisContainer.getHost(), redisContainer.getFirstMappedPort());
    }

    @Bean("testMailRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("testRedisConnectionFactory") RedisConnectionFactory testRedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(testRedisConnectionFactory);
        return redisTemplate;
    }
}
