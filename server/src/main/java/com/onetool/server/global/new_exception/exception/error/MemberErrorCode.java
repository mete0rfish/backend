package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCodeIfs {

    NON_EXIST_USER(HttpStatus.NOT_FOUND, "MEMBER-0001", "존재하지 않는 회원입니다"),
    ILLEGAL_LOGOUT_USER(HttpStatus.BAD_REQUEST, "MEMBER-0002", "이미 로그아웃된 회원입니다.");


    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}


