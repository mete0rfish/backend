package com.onetool.server.api.qna.dto.response;

import java.time.LocalDateTime;

public record QnaDetailReplyResponse(
        String content,
        String writer,
        LocalDateTime writtenTime
) {
}
