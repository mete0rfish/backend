package com.onetool.server.member.service;

import com.onetool.server.global.auth.MemberAuthContext;
import com.onetool.server.member.Member;
import com.onetool.server.member.MemberRepository;
import com.onetool.server.member.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        MemberAuthContext context = MemberAuthContext.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRole().name())
                .password(member.getPassword())
                .name(member.getName())
                .build();

        return new PrincipalDetails(context);
    }
}
