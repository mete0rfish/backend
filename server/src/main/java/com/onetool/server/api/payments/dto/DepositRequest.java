package com.onetool.server.api.payments.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record DepositRequest(
        Long orderId,
        String accountName,
        String accountNumber,
        String bankName,
        Long totalPrice,
        Set<Long> blueprintIds
) {

}
