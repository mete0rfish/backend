package com.onetool.server.global.auth;

import io.jsonwebtoken.Claims;

public interface AuthorizationProvider {

    String create(MemberAuthContext context);

    Claims parseClaims(String token);

    boolean validateToken(String token);

    public Long getUserId(String token);
}
