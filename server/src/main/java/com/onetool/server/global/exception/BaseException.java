package com.example.exception.common;

import com.example.exception.common.codes.BaseCode;
import com.example.exception.common.reason.Reason.*;
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