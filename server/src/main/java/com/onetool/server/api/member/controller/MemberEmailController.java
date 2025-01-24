package com.onetool.server.api.member.controller;

import com.onetool.server.api.member.dto.MemberFindEmailRequest;
import com.onetool.server.api.member.dto.MemberFindPwdRequest;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("/users/email")
public class MemberEmailController {

    private final MemberService memberService;

    @PostMapping("/find-email")
    public ApiResponse<?> findEmail(@RequestBody MemberFindEmailRequest request) {
        String email = memberService.findEmail(request);
        return ApiResponse.onSuccess(email);
    }

    @PostMapping("/find-password")
    public ApiResponse<?> findPwdCheck(@RequestBody MemberFindPwdRequest request) {
        boolean successFlag = memberService.findLostPwd(request);
        if (successFlag) {
            return ApiResponse.onSuccess("이메일을 발송했습니다.");
        } else {
            return ApiResponse.onFailure("403", "이메일 발송 과정에서 오류가 발생했습니다.", null);
        }
    }

    @PostMapping("/verification-requests")
    public ApiResponse<?> sendMessage(@RequestParam("email") @Valid String email) {
        memberService.sendCodeToEmail(email);
        return ApiResponse.onSuccess("이메일이 발송되었습니다.");
    }

    @GetMapping("/verifications")
    public ApiResponse<?> verificationEmail(@RequestParam("email") @Valid String email,
                                            @RequestParam("code") String authCode) {
        boolean response = memberService.verifiedCode(email, authCode);
        return (response) ? ApiResponse.onSuccess("이메일이 인증되었습니다.")
                : ApiResponse.onFailure("404", "코드가 일치하지 않습니다.", null);
    }
}
