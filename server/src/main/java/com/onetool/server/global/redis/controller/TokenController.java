package com.onetool.server.global.redis.controller;

import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.redis.domain.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;

    @GetMapping("/refresh/{id}")
    public ApiResponse<?> getRefresh(@PathVariable Long id) {
        String refreshToken = jwtUtil.createRefreshToken(id, Instant.now());
        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .refreshToken(refreshToken)
                .build();
        return ApiResponse.onSuccess(response);
    }
}
