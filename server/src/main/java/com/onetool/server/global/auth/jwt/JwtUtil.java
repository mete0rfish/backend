package com.onetool.server.global.auth.jwt;

import com.onetool.server.global.auth.AuthorizationProvider;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.auth.MemberCredential;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil implements AuthorizationProvider {

    private static final String USER_NAME = "name";
    private static final String USER_ROLE = "role";

    private final String secretKey;
    private final Long expirationMilliSec;

    public JwtUtil(
            @Value("${onetool.jwt.secrekey}") String secretKey,
            @Value("${onetool.jwt.expiration_time}") Long expirationMilliSec
    ) {
        this.secretKey = secretKey;
        this.expirationMilliSec = expirationMilliSec;
    }

    @Override
    public MemberCredential create(MemberAuthContext context) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMilliSec);

        String tokenValue = Jwts.builder()
                .claim(USER_NAME, context.name())
                .claim(USER_ROLE, context.role())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        return new MemberCredential(tokenValue);
    }

    @Override
    public MemberAuthContext parseCredential(MemberCredential token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .build()
                .parseClaimsJws(token.authorization())
                .getBody();
        return new MemberAuthContext(
                claims.get(USER_NAME, String.class),
                claims.get(USER_ROLE, String.class)
        );
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
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
}
