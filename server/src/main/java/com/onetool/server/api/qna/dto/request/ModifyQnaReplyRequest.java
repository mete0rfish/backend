package com.onetool.server.api.qna.dto.request;

import jakarta.validation.constraints.NotNull;

public record ModifyQnaReplyRequest(
        @NotNull
        Long replyId,
        String content
){}