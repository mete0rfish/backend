package com.onetool.server.api.member.controller;

import com.onetool.server.api.member.business.MemberBusiness;
import com.onetool.server.api.member.business.MemberEmailBusiness;
import com.onetool.server.api.member.dto.MemberFindEmailRequest;
import com.onetool.server.api.member.dto.MemberFindPwdRequest;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users/email")
public class MemberEmailController {

    private final MemberBusiness memberBusiness;
    private final MemberEmailBusiness memberEmailBusiness;

    @PostMapping("/find-email")
    public ApiResponse<?> findEmail(@RequestBody MemberFindEmailRequest request) {
        String email = memberBusiness.findEmail(request);
        return ApiResponse.onSuccess(email);
    }

    @PostMapping("/find-password")
    public ApiResponse<?> findPwdCheck(@RequestBody MemberFindPwdRequest request) {
        memberEmailBusiness.findLostPwd(request);
        return ApiResponse.onSuccess("이메일을 발송했습니다.");
    }

    @PostMapping("/verification-requests")
    public ApiResponse<?> sendMessage(@RequestParam("email") String email) {
        log.info("request email: {}", email);
        memberEmailBusiness.sendCodeToEmail(email);
        return ApiResponse.onSuccess("이메일이 발송되었습니다.");
    }

    @GetMapping("/verifications")
    public ApiResponse<?> verificationEmail(
            @RequestParam("email") @Valid String email,
            @RequestParam("code") String authCode
    ) {
        boolean response = memberEmailBusiness.verifiedCode(email, authCode);
        return (response) ? ApiResponse.onSuccess("이메일이 인증되었습니다.")
                : ApiResponse.onFailure("404", "코드가 일치하지 않습니다.", null);
    }
}
