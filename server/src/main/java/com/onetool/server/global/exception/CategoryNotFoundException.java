package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.BaseCode;
import com.onetool.server.global.exception.codes.ErrorCode;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(final String categoryName) {
        super(categoryName, ErrorCode.CATEGORY_NOT_FOUND);
    }
}