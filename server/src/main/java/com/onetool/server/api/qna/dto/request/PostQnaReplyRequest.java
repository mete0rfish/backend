package com.onetool.server.api.qna.dto.request;

import com.onetool.server.api.qna.QnaReply;
import jakarta.validation.constraints.Size;

public record PostQnaReplyRequest(
        @Size(min = 2, max = 100, message = "내용은 2 ~ 100자 이여야 합니다.")
        String content
) {

    public QnaReply fromRequestTooQnaReply(PostQnaReplyRequest request) {
        return QnaReply.builder()
                .content(request.content())
                .build();
    }
}