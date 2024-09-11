package com.onetool.server.global.redis.controller;

import com.onetool.server.global.auth.AuthService;
import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.redis.domain.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/auth")
public class TokenController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/validate")
    public void validate(@RequestHeader("Authorization") String requestAccessToken) {
        authService.validate(requestAccessToken);
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String refreshToken) {
        log.info("reissue token: {}", refreshToken);
        Map<String, String> tokens = authService.reissue(refreshToken);

        if (tokens != null) {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", tokens.get("refreshToken"))
                    .maxAge(860000)
                    .httpOnly(true)
                    .secure(true)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.get("accessToken"))
                    .build();
        } else {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }
}
