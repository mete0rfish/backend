package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.ErrorCode;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(final String email) {
        super(email, ErrorCode.EMAIL_NOT_FOUND);
    }
}
