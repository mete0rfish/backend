package com.onetool.server.global.auth;

import io.jsonwebtoken.Claims;

import java.time.Instant;

public interface AuthorizationProvider {

    String create(MemberAuthContext context);

    Claims parseClaims(String token);

    boolean validateToken(String token);

    public Long getUserId(String token);

    public String createRefreshToken(Long userId, Instant issuedAt);
}
