package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException(final Long depositId) {
        super(depositId.toString(), ErrorCode.DEPOSIT_NOT_FOUND);
    }
}
