package com.onetool.server.api.payments.controller;

import com.onetool.server.api.payments.dto.DepositRequest;
import com.onetool.server.api.payments.dto.DepositResponse;
import com.onetool.server.api.payments.service.DepositService;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/deposit")
    public ApiResponse<?> createPayment(@RequestBody DepositRequest request) {
        depositService.createDeposit(request);
        return ApiResponse.onSuccess("결제 항목 추가가 완료되었습니다.");
    }

    @GetMapping("/deposit")
    public ApiResponse<?> getPayment(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long userId = principalDetails.getContext().getId();
        List<DepositResponse> responses = depositService.getDeposits(userId);
        return ApiResponse.onSuccess(responses);
    }
}
