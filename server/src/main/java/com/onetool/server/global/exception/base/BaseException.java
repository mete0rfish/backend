package com.onetool.server.global.exception.base;
import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.reason.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private BaseCode code;

    public BaseException(String message, BaseCode code) {
        super(message);
        this.code = code;
    }

    public Reason.ReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}