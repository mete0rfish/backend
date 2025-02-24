package com.onetool.server.api.qna.service;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.QnaBoardRequest;

import java.security.Principal;
import java.util.List;

public interface QnaBoardService {

    List<QnaBoard> findAllQnaBoardList();

    void postQna(Member member, QnaBoard qnaBoard);

    void deleteQna(QnaBoard qnaBoard, Member member);

    void updateQna(QnaBoard qnaBoard);

    //에러 확인 메서드
    QnaBoard findQnaBoard(Long boardId);

    void hasErrorWithNoContent(List<QnaBoard> data);

    Member findMember(Principal principal);
}