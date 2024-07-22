package com.onetool.server.global.handler;

import com.onetool.server.global.exception.BaseException;
import com.onetool.server.global.exception.codes.BaseCode;

public class MyExceptionHandler extends BaseException {
    public MyExceptionHandler (BaseCode code){
        super(code);
    }
}
