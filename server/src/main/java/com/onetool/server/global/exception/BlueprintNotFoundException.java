package com.onetool.server.global.exception;

import com.onetool.server.global.exception.base.NotFoundException;
import com.onetool.server.global.exception.codes.ErrorCode;

public class BlueprintNotFoundException extends NotFoundException {
    public BlueprintNotFoundException(final String blueprintName) {
        super(blueprintName, ErrorCode.NO_BLUEPRINT_FOUND);
    }
}