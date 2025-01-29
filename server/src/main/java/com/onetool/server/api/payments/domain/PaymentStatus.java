package com.onetool.server.api.payments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum PaymentStatus {
    PAY_PENDING("결제대기"),
    PAY_DONE("결제완료"),
    REFUND_PENDING("환불대기"),
    REFUND_DONE("환불완료");
    
    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
