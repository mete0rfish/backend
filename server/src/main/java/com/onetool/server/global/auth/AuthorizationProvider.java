package com.onetool.server.global.auth;

import com.onetool.server.global.auth.login.PrincipalDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.Map;

public interface AuthorizationProvider {

    Map<String, String> createTokens(MemberAuthContext context);

    Claims parseClaims(String token);

    boolean validateToken(String token);

    Long getUserId(String token);

    Authentication getAuthentication(String token);

    Authentication getAuthenticationByRefreshToken(String refreshToken);
}
