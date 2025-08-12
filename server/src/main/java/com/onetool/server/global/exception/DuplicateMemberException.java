package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.DuplicateException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class DuplicateMemberException extends DuplicateException {
    public DuplicateMemberException(final String email) {
        super(email, ErrorCode.DUPLICATE_MEMBER);
    }
}
