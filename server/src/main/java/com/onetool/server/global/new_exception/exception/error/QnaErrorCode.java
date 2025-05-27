package com.onetool.server.global.new_exception.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@Getter
public enum QnaErrorCode implements ErrorCodeIfs{

    //Qna 에러
    NO_QNA_CONTENT(NO_CONTENT, "QNA-0000", "게시된 문의사항이 없습니다."),
    UNAVAILABLE_TO_MODIFY(FORBIDDEN, "QNA-0001", "게시글에 대한 권한이 없습니다."),
    NO_QNA_REPLY(NO_CONTENT, "QNA-0002", "유효한 댓글이 아닙니다."),
    NULL_POINT_ERROR(HttpStatus.NOT_FOUND, "QNA-0010", "해당 객체는 NULL입니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "QNA-0011", "해당 객체는 서버에 존재하지 않습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String serverCode;
    private final String description;
}
