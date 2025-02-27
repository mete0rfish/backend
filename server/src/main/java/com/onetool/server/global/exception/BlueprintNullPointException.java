package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class BlueprintNullPointException extends NotFoundException {
    public BlueprintNullPointException(String errorMessage) {
        super(errorMessage, ErrorCode.NULL_POINT_ERROR);
    }
}
