package com.onetool.server.api.payments.controller;

import com.onetool.server.api.payments.business.PaymentBusiness;
import com.onetool.server.api.payments.dto.PaymentRequest;
import com.onetool.server.api.payments.dto.PaymentResponse;
import com.onetool.server.global.auth.login.PrincipalDetails;
import com.onetool.server.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments/deposit")
public class PaymentController {

    private final PaymentBusiness paymentBusiness;

    @GetMapping
    public ApiResponse<List<PaymentResponse>> getPayment(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getContext().getId();
        List<PaymentResponse> responses = paymentBusiness.getPaymentList(userId);
        return ApiResponse.onSuccess(responses);
    }

    @PostMapping
    public ApiResponse<String> createPayment(@RequestBody PaymentRequest request) {
        Long paymentId = paymentBusiness.createPayment(request);
        return ApiResponse.onSuccess(paymentId + "번 결제가 추가되었습니다.");
    }

    @DeleteMapping
    public ApiResponse<Long> deletePayment(@RequestBody Long paymentId) {
        paymentBusiness.removePayment(paymentId);
        return ApiResponse.onSuccess(paymentId);
    }
}
