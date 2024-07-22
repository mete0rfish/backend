package com.onetool.server.global.exception;

import com.example.exception.common.reason.Reason.*;
import com.onetool.server.global.exception.codes.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private BaseCode code;

    public ReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}