package com.onetool.server.global.auth.jwt;

import com.onetool.server.global.auth.AuthorizationProvider;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.redis.domain.Token;
import com.onetool.server.global.redis.repository.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil implements AuthorizationProvider {

    private static final String USER_NAME = "name";
    private static final String USER_ROLE = "role";

    private final SecretKey key;
    private final Long expirationMilliSec;
    // TODO application으로 지정하기
    private static final int refreshTokenExpirationMillis = 864_000_000;
    private final TokenRepository tokenRepository;

    public JwtUtil(
            @Value("${onetool.jwt.secrekey}") String secretKey,
            @Value("${onetool.jwt.expiration_time}") Long expirationMilliSec,
            TokenRepository tokenRepository) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.expirationMilliSec = expirationMilliSec;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String create(MemberAuthContext context) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", context.getId());
        claims.put("email", context.getEmail());
        claims.put("role", context.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expirationMilliSec);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    @Override
    public Long getUserId(String token) {
        Double db = parseClaims(token).get("memberId", Double.class);
        return db.longValue();
    }

    @Override
    public String createRefreshToken(Long id, Instant issuedAt) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plusMillis(refreshTokenExpirationMillis)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Token token = Token.builder()
                .refreshToken(refreshToken)
                .build();

        tokenRepository.save(token);
        return refreshToken;
    }
}
