package com.onetool.server.api.member.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.LoginRequest;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.exception.IllegalLogoutMember;
import com.onetool.server.global.exception.codes.ErrorCode;
import com.onetool.server.global.redis.service.TokenBlackListRedisService;
import com.onetool.server.global.redis.service.TokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Business
@RequiredArgsConstructor
@Slf4j
public class MemberLoginBusiness {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    private final TokenRedisService tokenRedisService;
    private final TokenBlackListRedisService tokenBlackListRedisService;
    private final MemberService memberService;

    public Map<String, String> login(LoginRequest request) {
        Member member = memberService.findByEmail(request.getEmail());

        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            log.error("비밀번호 불일치: " + encoder.encode(member.getPassword()));
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        MemberAuthContext context = MemberAuthContext.from(member);
        return jwtUtil.createTokens(context);
    }

    public ApiResponse<String> logout(String accessToken, String email) {
        Long expiration = jwtUtil.getExpirationMilliSec(accessToken);
        tokenBlackListRedisService.setBlackList(accessToken, expiration);
        if (tokenRedisService.hasKey(email)) {
            tokenRedisService.deleteValues(email);
        } else {
            throw new IllegalLogoutMember(ErrorCode.ILLEGAL_LOGOUT_USER);
        }
        return ApiResponse.onSuccess("로그아웃이 완료되었습니다.");
    }
}
