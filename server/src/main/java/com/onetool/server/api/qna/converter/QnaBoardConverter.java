package com.onetool.server.api.qna.converter;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.response.QnaBoardBriefResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardDetailResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardResponse;
import com.onetool.server.api.qna.dto.response.QnaReplyResponse;
import com.onetool.server.global.annotation.Converter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Converter
public class QnaBoardConverter {

    public List<QnaBoardBriefResponse> fromQnaBoardListToBriefResponseList(List<QnaBoard> qnaBoards) {
        return qnaBoards.stream()
                .map(this::fromQnaBoardToBriefResponse)
                .toList();
    }

    public QnaBoardBriefResponse fromQnaBoardToBriefResponse(QnaBoard qnaBoard) {
        return QnaBoardBriefResponse.builder()
                .title(qnaBoard.getTitle())
                .writer(qnaBoard.getMember().getName())
                .postDate(qnaBoard.getCreatedAt().toLocalDate())
                .views(qnaBoard.getViews())
                .replies(qnaBoard.getQnaReplies().size())
                .build();
    }

    public QnaBoardDetailResponse fromQnaBoardToDetailResponse(QnaBoard qnaBoard, boolean authorization) {
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

    public QnaBoard fromRequestToQnaBoard(PostQnaBoardRequest request){
        return new QnaBoard(request.title(),request.content());
    }
}
