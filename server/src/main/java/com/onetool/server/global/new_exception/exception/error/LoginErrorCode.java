package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoginErrorCode implements ErrorCodeIfs{

    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN-0001", "이메일이 잘못됨"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-0000", "잘못된 요청입니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "COMMON-0002", "이미 존재하는 회원입니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST, "COMMON-0003", "기존 회원 정보와 중복됩니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "COMMON-0004", "유효하지 않은 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
