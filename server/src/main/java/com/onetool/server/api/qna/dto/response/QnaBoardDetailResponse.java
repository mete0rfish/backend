package com.onetool.server.api.qna.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QnaBoardDetailResponse(
        String title,
        String content,
        String writer,
        LocalDate postDate,
        List<QnaReplyResponse.QnaDetailsReplyResponse> replies,
        boolean authorization
) {
}
