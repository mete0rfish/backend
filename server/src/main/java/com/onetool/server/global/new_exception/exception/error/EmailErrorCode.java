package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailErrorCode {

    // 이메일 관련
    EMAIL_ERROR(HttpStatus.BAD_REQUEST, "EMAIL-0001", "이메일 전송 중 오류가 발생했습니다."),
    AUTH_CODE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL-0002", "인증코드 생성 중 문제가 발생했습니다."),
    RANDOM_PASSWORD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL-0003", "랜덤 패스워드 생성 중 문제가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
