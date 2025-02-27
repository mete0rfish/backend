package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode implements ErrorCodeIfs {

    //카테고리 에러
    CATEGORY_NOT_FOUND(NOT_FOUND, "CATEGORY-0000", "존재하지 않는 카테고리입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
