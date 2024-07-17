package com.onetool.server.member;

import com.onetool.server.global.exception.MemberNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity handleNotFoundMemberException(MemberNotFoundException e) {
        return ResponseEntity.badRequest().body("유저를 찾을 수 없습니다.");
    }
}
