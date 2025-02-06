package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class BlueprintNotApprovedException extends BaseException {

    public BlueprintNotApprovedException(String blueprintId){
        super(blueprintId, ErrorCode.BLUEPRINT_NOT_APPROVED);
    }
}