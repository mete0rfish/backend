package com.onetool.server.global.exception.base;

import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.ErrorCode;

public class NotFoundException extends BaseException {

    private final String value;

    public NotFoundException(String value) {
        super(value, ErrorCode.NOT_FOUND_ERROR);
        this.value = value;
    }

    public NotFoundException(String value, BaseCode code) {
        super(value, code);
        this.value = value;
    }
}
