package com.onetool.server.member;

import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.global.auth.MemberCredential;
import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.exception.MemberNotFoundException;
import com.onetool.server.member.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException("이메일이 존재하지 않습니다."));
        if(!encoder.matches(password, member.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        MemberAuthContext context = new MemberAuthContext(
                member.getName(),
                member.getRole().name()
        );

        MemberCredential memberCredential = jwtUtil.create(context);
        return memberCredential.authorization();
    }
}
