package com.onetool.server.global.new_exception.exception;


import com.onetool.server.global.new_exception.exception.error.ErrorCode;
import com.onetool.server.global.new_exception.exception.error.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCodeIfs errorCode;
    private String customErrorMessage;

    private ApiException(ErrorCodeIfs code) {
        this.errorCode = code;
    }

    private ApiException(ErrorCodeIfs code, final String message) {
        this.errorCode = code;
        this.customErrorMessage = message;
    }

    public boolean hasCustomMessage(){
        return customErrorMessage != null;
    }

    public static ApiException from(ErrorCodeIfs errorCode) {
        return new ApiException(errorCode ,null);
    }

    public static ApiException from(ErrorCodeIfs errorCode, final String customErrorMessage){
        return new ApiException(errorCode, customErrorMessage);
    }
}
