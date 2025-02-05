package com.onetool.server.api.mail;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.BusinessLogicException;
import com.onetool.server.global.exception.codes.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public void sendEmail(
            String toEmail,
            String title,
            String text,
            boolean isAuthCode
    ) {
        MimeMessage emailForm = createEmailForm(toEmail, title, text, isAuthCode);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.error("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            throw new BusinessLogicException();
        }
    }

    // 발신할 이메일 데이터 세팅
    private MimeMessage createEmailForm(
            String toEmail,
            String title,
            String text,
            boolean isAuthCode
    ) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject(title);
            messageHelper.setTo(toEmail);
            messageHelper.setText(setText(text, isAuthCode), true);
            return message;
        } catch (MessagingException e) {
            throw new BaseException(ErrorCode.BAD_REQUEST_ERROR);
        }
    }

    private String setText(String code, boolean isAuthCode) {
        Context context = new Context();
        if(isAuthCode) {
            context.setVariable("code", code);
            return templateEngine.process("authcode", context);
        } else {
            context.setVariable("password", code);
            return templateEngine.process("password", context);
        }
    }
}