package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCodeIfs{

    PAYMENT_NOT_FOUND(NOT_FOUND, "PAYMENT-001", "결제가 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
