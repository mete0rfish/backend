package com.example.exception.common.codes;

import com.example.exception.common.reason.Reason;

public interface BaseCode {
    public Reason.ReasonDto getReasonHttpStatus();
}
