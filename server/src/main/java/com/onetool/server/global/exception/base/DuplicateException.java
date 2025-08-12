package com.onetool.server.global.exception.base;

import com.onetool.server.global.exception.codes.ErrorCode;

public class DuplicateException extends BaseException {

    private final String value;

    public DuplicateException(String value) {
        super(value, ErrorCode.DUPLICATE);
        this.value = value;
    }

    public DuplicateException(String value, ErrorCode errorCode) {
        super(value, errorCode);
        this.value = value;
    }
}
