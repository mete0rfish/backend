package com.example.exception.handler;

import com.example.exception.common.BaseException;
import com.example.exception.common.codes.BaseCode;

public class MyExceptionHandler extends BaseException {
    public MyExceptionHandler (BaseCode code){
        super(code);
    }
}
