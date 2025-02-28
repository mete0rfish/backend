package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoginErrorCode implements ErrorCodeIfs {

    INVALID_EMAIL(HttpStatus.NOT_FOUND, "LOGIN-0001", "이메일이 잘못되었습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "LOGIN-0002", "비밀번호가 잘못되었습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "COMMON-0003", "유효하지 않은 토큰입니다."),
    ILLEGAL_LOGOUT_USER(HttpStatus.BAD_REQUEST, "MEMBER-0004", "이미 로그아웃된 회원입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
