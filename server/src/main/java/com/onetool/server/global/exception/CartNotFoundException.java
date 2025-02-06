package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.ErrorCode;

public class CartNotFoundException extends NotFoundException {

    public CartNotFoundException(String value) {
        super(value, ErrorCode.CART_NOT_FOUND);
    }

    public CartNotFoundException(String value, BaseCode code) {
        super(value, code);
    }
}
