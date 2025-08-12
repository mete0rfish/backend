package com.onetool.server.api.qna.dto.response;

import com.onetool.server.api.qna.QnaBoard;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QnaBoardBriefResponse(
        String title,
        String writer,
        LocalDate postDate,
        Long views,
        Integer replies
) {

    public static List<QnaBoardBriefResponse> fromQnaBoardListToBriefResponseList(List<QnaBoard> qnaBoards) {
        return qnaBoards.stream()
                .map(QnaBoardBriefResponse::fromQnaBoardToBriefResponse)
                .toList();
    }

    public static QnaBoardBriefResponse fromQnaBoardToBriefResponse(QnaBoard qnaBoard) {
        return QnaBoardBriefResponse.builder()
                .title(qnaBoard.getTitle())
                .writer(qnaBoard.getMember().getName())
                .postDate(qnaBoard.getCreatedAt().toLocalDate())
                .views(qnaBoard.getViews())
                .replies(qnaBoard.getQnaReplies().size())
                .build();
    }
}
