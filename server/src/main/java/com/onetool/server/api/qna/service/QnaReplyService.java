package com.onetool.server.api.qna.service;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.QnaReply;
import com.onetool.server.api.qna.dto.request.QnaReplyRequest;

import java.security.Principal;


public interface QnaReplyService {
    void saveReply(Member member, QnaBoard qnaBoard, QnaReply qnaReply);

    void deleteReply(Member member, QnaBoard board, QnaReply qnaReply);

    void updateReply(Member member, String content, QnaReply qnaReply);

    Member findMember(Principal principal);

    QnaBoard findQnaBoard(Long id);

    QnaReply findQnaReply(Long id);
}