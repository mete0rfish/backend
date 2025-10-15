package com.onetool.server.redis;

import com.onetool.server.ServerApplication;
import com.onetool.server.api.helper.MockBeanInjection;
import com.onetool.server.global.config.AbstractContainerBaseTest;
import com.onetool.server.global.config.WebSocketConfig;
import com.onetool.server.global.redis.service.MailRedisService;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
@SpringBootTest(classes = {ServerApplication.class, RedisCrudTest.TestRedisConfig.class, WebSocketConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class RedisCrudTest extends AbstractContainerBaseTest {

    private Logger logger = LoggerFactory.getLogger(RedisCrudTest.class);

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    private MailRedisService mailRedisService;

    @Autowired
    @Qualifier("testMailRedisTemplate")
    private RedisTemplate<String, Object> testMailRedisTemplate;

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6-alpine"))
            .withExposedPorts(6379);

    @TestConfiguration
    static class TestRedisConfig {
        @Bean("testRedisConnectionFactory")
        public RedisConnectionFactory testRedisConnectionFactory() {
            return new LettuceConnectionFactory(redis.getHost(), redis.getFirstMappedPort());
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

    @BeforeEach
    void setUp() {
        logger.info("testRedis - host: {}, port: {}", redis.getHost(), redis.getFirstMappedPort());
        this.mailRedisService = new MailRedisService(testMailRedisTemplate);
        mailRedisService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        mailRedisService.deleteValues(KEY);
    }

    @Test
    @DisplayName("Redis에 데이터를 저장하면 정상적으로 조회된다.")
    void saveAndFindTest() throws Exception {
        // when
        String findValue = mailRedisService.getValues(KEY);

        // then
        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 수정할 수 있다.")
    void updateTest() throws Exception {
        // given
        String updateValue = "updateValue";
        mailRedisService.setValues(KEY, updateValue, DURATION);

        // when
        String findValue = mailRedisService.getValues(KEY);

        // then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 삭제할 수 있다.")
    void deleteTest() throws Exception {
        // when
        mailRedisService.deleteValues(KEY);
        String findValue = mailRedisService.getValues(KEY);

        // then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 저장된 데이터는 만료시간이 지나면 삭제된다.")
    void expiredTest() throws Exception {
        // when
        String findValue = mailRedisService.getValues(KEY);
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = mailRedisService.getValues(KEY);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }
}
