package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class OrdersNullPointException extends NotFoundException {

    public OrdersNullPointException(String errorMessage) {
        super(errorMessage, ErrorCode.NULL_POINT_ERROR);
    }
}
