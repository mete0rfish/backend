package com.onetool.server.global.new_exception.exception;

import com.onetool.server.global.new_exception.exception.error.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCodeIfs errorCode;
    private final String customErrorMessage;

    public ApiException(ErrorCodeIfs code) {
        super(code.getDescription());
        this.errorCode = code;
        this.customErrorMessage = null;
    }

    public ApiException(ErrorCodeIfs code, final String message) {
        super(message != null ? message : code.getDescription());
        this.errorCode = code;
        this.customErrorMessage = message;
    }

    public boolean hasCustomMessage() {
        return customErrorMessage != null;
    }

    @Override
    public String getMessage() {
        return hasCustomMessage() ? customErrorMessage : errorCode.getDescription();
    }

    public static ApiException from(ErrorCodeIfs errorCode) {
        return new ApiException(errorCode);
    }

    public static ApiException from(ErrorCodeIfs errorCode, final String customErrorMessage) {
        return new ApiException(errorCode, customErrorMessage);
    }
}
