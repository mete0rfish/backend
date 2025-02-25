package com.onetool.server.api.qna.dto.response;

import com.onetool.server.api.qna.QnaBoard;
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

    public static QnaBoardDetailResponse fromQnaBoardToDetailResponse(QnaBoard qnaBoard, boolean authorization) {
        return QnaBoardDetailResponse.builder()
                .title(qnaBoard.getTitle())
                .content(qnaBoard.getContent())
                .writer(qnaBoard.getMember().getName())
                .postDate(qnaBoard.getCreatedAt().toLocalDate())
                .replies(qnaBoard.getQnaReplies()
                        .stream()
                        .map(QnaReplyResponse.QnaDetailsReplyResponse::from)
                        .toList())
                .authorization(authorization)
                .build();
        //todo QnaReplyResponse.QnaDetailsReplyResponse::from << QnaApply부분은 추후 리팩토링 예정이라 현재는
        //todo 위와 같은형태로 유지
    }
}
