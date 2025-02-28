package com.onetool.server.api.member.business;

import com.onetool.server.api.mail.MailService;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.MemberFindPwdRequest;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.exception.BusinessLogicException;
import com.onetool.server.global.exception.codes.ErrorCode;
import com.onetool.server.global.redis.service.MailRedisService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static com.onetool.server.global.generator.RandomGenerator.createCode;
import static com.onetool.server.global.generator.RandomGenerator.createRandomPassword;

@Business
@RequiredArgsConstructor
@Slf4j
public class MemberEmailBusiness {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String title = "[원툴] 회원가입 이메일 인증번호";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final MailRedisService mailRedisService;
    private final MemberService memberService;

    @Transactional
    public void sendCodeToEmail(String toEmail) {
        String authCode = createCode();
        mailService.sendEmail(toEmail, title, authCode, true);
        mailRedisService.setValues(
                AUTH_CODE_PREFIX + toEmail,
                authCode,
                Duration.ofMillis(this.authCodeExpirationMillis)
        );
    }

    @Transactional(readOnly = true)
    public boolean verifiedCode(String email, String authCode) {
        String redisAuthCode = mailRedisService.getValues(AUTH_CODE_PREFIX + email);
        return mailRedisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
    }

    @Transactional
    public void findLostPwd(MemberFindPwdRequest request) {
        Member member = memberService.findByEmail(request.getEmail());
        String randomPassword = createRandomPassword();
        memberService.updatePassword(member, encoder.encode(randomPassword));
        mailService.sendEmail(
                member.getEmail(),
                "[원툴] 임시 비밀번호 발급",
                randomPassword,
                false
        );
    }
}
