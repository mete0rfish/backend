package com.onetool.server.global.new_exception.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeIfs {

    HttpStatus getHttpStatus();
    String getServerCode();
    String getDescription();
}
