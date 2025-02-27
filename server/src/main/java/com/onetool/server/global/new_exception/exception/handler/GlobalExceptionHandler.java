package com.onetool.server.global.new_exception.exception.handler;

import com.onetool.server.global.new_exception.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(
            Exception exception
    ){
        log.error("❌ [HTTP 500] Internal Server Error: ", exception);

        return ResponseEntity.status(500)
                .body(
                        ErrorResponse.generateErrorResponse(exception)
                );
    }
}
