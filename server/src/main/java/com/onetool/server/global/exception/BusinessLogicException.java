package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class BusinessLogicException extends BaseException {
    public BusinessLogicException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
