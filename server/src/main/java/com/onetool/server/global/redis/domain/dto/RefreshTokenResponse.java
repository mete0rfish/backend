package com.onetool.server.global.redis.domain.dto;

import lombok.Builder;

public record RefreshTokenResponse(
        String refreshToken
) {

    @Builder
    public RefreshTokenResponse(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
