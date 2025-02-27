package com.whale.gather_one.global.exception.handler;

import com.whale.gather_one.global.exception.BaseException;
import com.whale.gather_one.global.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {

    /**
     * 클라이언트 에러
     * 직접 생성한 예외에 대한 처리
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResponse onThrowException(BaseException baseException) {
        return ErrorResponse.generateErrorResponse(baseException);
    }
}
