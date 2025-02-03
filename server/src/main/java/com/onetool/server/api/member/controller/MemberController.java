package com.onetool.server.api.member.controller;

import com.onetool.server.api.member.dto.*;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.auth.jwt.JwtUtil;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import com.onetool.server.global.exception.MemberNotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;
import com.onetool.server.global.exception.codes.SuccessCode;
import com.onetool.server.api.qna.dto.response.QnaBoardResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Map;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse servletResponse
    ) {
        Map<String, String> tokens = memberService.login(request);
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(tokens.get("refreshToken"));
        servletResponse.setHeader("Set-Cookie", refreshTokenCookie.toString());
        return ApiResponse.onSuccess(tokens.get("accessToken"));
    }

    @DeleteMapping("logout")
    public ApiResponse<String> logout(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            HttpServletRequest servletRequest
    ) {
        String accessToken = jwtUtil.resolveToken(servletRequest);
        return memberService.logout(accessToken, principalDetails.getUsername());
    }

    @PostMapping("/signup")
    public ApiResponse<?> createMember(@RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.createMember(request);
        return ApiResponse.of(SuccessCode.CREATED, response);
    }

    @PatchMapping
    public ApiResponse<?> updateMember(
            @Valid @RequestBody MemberUpdateRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new MemberNotFoundException(ErrorCode.NON_EXIST_USER);
        }

        Long id = principalDetails.getContext().getId();
        memberService.updateMember(id, request);

        return ApiResponse.onSuccess("회원 정보가 수정되었습니다.");
    }

    @DeleteMapping
    public ApiResponse<?> deleteMember(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        if (principalDetails == null) {
            throw new MemberNotFoundException(ErrorCode.NON_EXIST_USER);
        }

        Long id = principalDetails.getContext().getId();
        memberService.deleteMember(id);

        return ApiResponse.onSuccess("회원 탈퇴가 완료되었습니다.");
    }

    @GetMapping
    public ApiResponse<?> getMemberInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new MemberNotFoundException(ErrorCode.NON_EXIST_USER);
        }

        Long id = principalDetails.getContext().getId();

        try {
            MemberInfoResponse memberResponse = memberService.getMemberInfo(id);
            return ApiResponse.onSuccess(memberResponse);
        } catch (RuntimeException e) {
            return ApiResponse.onFailure("404", "회원을 찾을 수 없습니다.", null);
        }
    }

    // TODO : uri 수정 필요
    @GetMapping("/myQna")
    public ApiResponse<List<QnaBoardResponse.QnaBoardBriefResponse>> getMyQna(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("context id: {}", principalDetails.getContext().getId());
        return ApiResponse.onSuccess(memberService.findQnaWrittenById(principalDetails.getContext()));
    }

    @GetMapping("/myPurchase")
    public ApiResponse<List<BlueprintDownloadResponse>> getMyPurchases(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getContext().getId();
        List<BlueprintDownloadResponse> blueprints = memberService.getPurchasedBlueprints(userId);
        return ApiResponse.onSuccess(blueprints);
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}