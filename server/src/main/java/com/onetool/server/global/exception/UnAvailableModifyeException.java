package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class UnAvailableModifyeException extends BaseException {
    public UnAvailableModifyeException(String errorMessage) {
        super(errorMessage, ErrorCode.UNAVAILABLE_TO_MODIFY);
    }
}
