package com.onetool.server.global.new_exception.exception.handler;

import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.ErrorCodeIfs;
import com.onetool.server.global.new_exception.exception.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    public ResponseEntity<Object> apiException(
            ApiException apiException
    ) {
        ErrorCodeIfs errorCode = apiException.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.generateErrorResponse(apiException)
                );
    }
}
