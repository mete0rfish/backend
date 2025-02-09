package com.onetool.server.global.exception.base;

import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.ErrorCode;

public class InvalidException extends BaseException {

    private final String value;

    public InvalidException(String value) {
        super(value, ErrorCode.INVALID_TYPE_VALUE);
        this.value = value;
    }

    public InvalidException(String value, BaseCode code) {
        super(value, code);
        this.value = value;
    }
}
