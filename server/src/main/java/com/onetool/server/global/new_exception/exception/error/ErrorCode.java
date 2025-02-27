package com.whale.gather_one.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ;

    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}
