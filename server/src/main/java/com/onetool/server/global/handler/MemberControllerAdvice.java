package com.onetool.server.global.handler;

import com.onetool.server.global.exception.DuplicateMemberException;
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

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity handleDuplicateMemberException(DuplicateMemberException e) {
        return ResponseEntity.badRequest().body("이메일이 중복됩니다.");
    }
}
