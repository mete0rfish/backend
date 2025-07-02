package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Getter
public enum BlueprintErrorCode implements ErrorCodeIfs{

    NULL_POINT_ERROR(HttpStatus.NOT_FOUND, "BLUEPRINT-0010", "해당 객체는 NULL입니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "BLUEPRINT-0011", "해당 객체는 서버에 존재하지 않습니다"),
    NO_BLUEPRINT_FOUND(NOT_FOUND, "BLUEPRINT-0000", "도면이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
