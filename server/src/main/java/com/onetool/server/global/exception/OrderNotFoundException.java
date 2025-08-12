package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class OrderNotFoundException extends NotFoundException {

    public OrderNotFoundException(final Long orderId) {
        super(orderId.toString(), ErrorCode.ORDER_NOT_FOUND);
    }
}
