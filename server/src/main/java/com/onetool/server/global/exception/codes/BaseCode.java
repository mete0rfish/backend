package com.onetool.server.global.exception.codes;

import com.example.exception.common.reason.Reason;

public interface BaseCode {
    public Reason.ReasonDto getReasonHttpStatus();
}
