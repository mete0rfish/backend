package com.onetool.server.global.auth.filter;

import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.auth.login.service.CustomUserDetailsService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SecurityContextRepository securityContextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        //JWT가 헤더에 있는 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.info("토큰: " + token);
            //JWT 유효성 검증
            if (checkAccessTokenValid(token)) {
                log.info("validation 통과");
                Long userId = jwtUtil.getUserId(token);

                //유저와 토큰 일치 시 principalDetails 생성
                PrincipalDetails principalDetails = customUserDetailsService.loadUserByUsername(userId.toString());

                if (principalDetails != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(principalDetails, token, principalDetails.getAuthorities());

                    log.info("필터 설정됨: ");

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.setContext(securityContext);

                    securityContextRepository.saveContext(securityContext, request, response);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkAccessTokenValid(String accessToken) {
        String id = jwtUtil.getUserId(accessToken).toString();
        customUserDetailsService.saveUserInSecurityContext(id, accessToken);
        return jwtUtil.validateToken(accessToken);
    }
}
