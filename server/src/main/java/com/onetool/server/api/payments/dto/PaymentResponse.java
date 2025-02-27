package com.onetool.server.api.payments.dto;

import com.onetool.server.api.payments.domain.Payment;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record PaymentResponse(
        Long id,
        String accountName,
        String accountNumber,
        String bankName,
        Long totalPrice
) {

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                        .id(payment.getId())
                        .accountName(payment.getAccountName())
                        .accountNumber(payment.getAccountNumber())
                        .bankName(payment.getBankName())
                        .totalPrice(payment.getTotalPrice())
                        .build();
    }
}
