package com.onetool.server.redis;

import com.onetool.server.ServerApplication;
import com.onetool.server.global.config.RedisTestContainerBase;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
@SpringBootTest
        (classes = {ServerApplication.class, WebSocketConfig.class, RedisTestContainerBase.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class RedisCrudTest extends RedisTestContainerBase {

    private Logger logger = LoggerFactory.getLogger(RedisCrudTest.class);

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    private MailRedisService mailRedisService;

    @Autowired
    @Qualifier("testMailRedisTemplate")
    private RedisTemplate<String, Object> testMailRedisTemplate;

    @BeforeEach
    public void setUp() {
        mailRedisService = new MailRedisService(testMailRedisTemplate);
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
