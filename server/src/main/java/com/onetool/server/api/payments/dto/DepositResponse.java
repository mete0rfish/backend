package com.onetool.server.api.payments.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record DepositResponse(
        Long id,
        String accountName,
        String accountNumber,
        String bankName,
        Long totalPrice
) {

}
