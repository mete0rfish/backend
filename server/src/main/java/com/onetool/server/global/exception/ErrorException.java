package com.onetool.server.global.exception;

public class ErrorException extends RuntimeException{
    public ErrorException(String errorMessage) {
        super(errorMessage);
    }
}
