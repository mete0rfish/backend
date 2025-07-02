package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCodeIfs {

    NON_EXIST_USER(HttpStatus.NOT_FOUND, "MEMBER-0001", "존재하지 않는 회원입니다"),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER-0002", "이미 존재하는 회원입니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER-0003", "기존 회원 정보와 중복됩니다."),
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}


