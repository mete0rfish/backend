package com.onetool.server.api.qna.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QnaBoardBriefResponse(
        String title,
        String writer,
        LocalDate postDate,
        Long views,
        Integer replies
) {
}
