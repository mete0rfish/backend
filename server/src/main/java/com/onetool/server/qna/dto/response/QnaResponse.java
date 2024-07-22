package com.onetool.server.qna.dto.response;

import com.onetool.server.qna.QnaBoard;
import lombok.Builder;

import java.time.LocalDate;

public class QnaResponse {

    @Builder
    public static record QnaBoardBriefResponse(
            String title,
            String writer,
            LocalDate postDate
    ){
        public static QnaBoardBriefResponse brief(QnaBoard qnaBoard){
            return new QnaBoardBriefResponse(qnaBoard.getTitle(), qnaBoard.getMember().getName(), qnaBoard.getCreatedAt().toLocalDate());
        }

    }

    @Builder
    public static record QnaBoardDetailResponse(
            String title,
            String content,
            String writer,
            LocalDate postDate
    ){
        public static QnaBoardDetailResponse details(QnaBoard qnaBoard){
            return new QnaBoardDetailResponse(qnaBoard.getTitle(), qnaBoard.getMember().getName(), qnaBoard.getContent(), qnaBoard.getCreatedAt().toLocalDate());
        }

    }
}
