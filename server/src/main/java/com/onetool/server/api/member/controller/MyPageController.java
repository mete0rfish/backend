package com.onetool.server.api.member.controller;

import com.onetool.server.api.member.dto.BlueprintDownloadResponse;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.api.qna.dto.response.QnaBoardResponse;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class MyPageController {

    private final MemberService memberService;

    // TODO : uri 수정 필요
    @GetMapping("/myQna")
    public ApiResponse<List<QnaBoardResponse.QnaBoardBriefResponse>> getMyQna(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.onSuccess(memberService.findQnaWrittenById(principalDetails.getContext()));
    }

    @GetMapping("/myPurchase")
    public ApiResponse<List<BlueprintDownloadResponse>> getMyPurchases(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getContext().getId();
        List<BlueprintDownloadResponse> blueprints = memberService.getPurchasedBlueprints(userId);
        return ApiResponse.onSuccess(blueprints);
    }
}
