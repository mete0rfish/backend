package com.onetool.server.api.member.service;

import com.onetool.server.global.exception.*;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;

import com.onetool.server.api.member.repository.MemberRepository;
import com.onetool.server.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findByNameAndPhoneNumber(String name, String phoneNumber) {
        return memberRepository.findByNameAndPhoneNum(name, phoneNumber).orElseThrow(() -> new MemberNotFoundException(name));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException(email));
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id.toString()));
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void updatePassword(Member member, String encodedNewPassword) {
        member.setPassword(encodedNewPassword);
        memberRepository.save(member);
    }

    public void validateExistId(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new BaseException(ErrorCode.NON_EXIST_USER);
        }
    }

    public void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BaseException(ErrorCode.EXIST_EMAIL);
        }
    }

    public Member findMemberWithCartById(Long id) {
        return memberRepository
                .findByIdWithCart(id)
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }
}
