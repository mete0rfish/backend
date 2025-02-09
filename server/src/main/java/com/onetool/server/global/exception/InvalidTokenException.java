package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.InvalidException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class InvalidTokenException extends InvalidException {
    public InvalidTokenException(final String token) {
        super(token, ErrorCode.INVALID_TOKEN);
    }
}
