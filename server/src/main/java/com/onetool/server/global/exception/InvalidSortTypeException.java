package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.InvalidException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class InvalidSortTypeException extends InvalidException {
    public InvalidSortTypeException(final String sortType) {
        super(sortType, ErrorCode.INVALID_SORT_TYPE);
    }
}