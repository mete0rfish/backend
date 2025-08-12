package com.onetool.server.global.redis.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 10L)
@AllArgsConstructor
@Getter
@ToString
public class Token {

    @Id
    private Long id;
    private String refreshToken;

    @Builder
    public Token(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
