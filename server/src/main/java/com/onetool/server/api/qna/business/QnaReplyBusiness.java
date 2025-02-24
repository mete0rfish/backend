package com.onetool.server.api.qna.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.QnaReply;
import com.onetool.server.api.qna.controller.QnaReplyController;
import com.onetool.server.api.qna.converter.QnaReplyConverter;
import com.onetool.server.api.qna.dto.request.ModifyQnaReplyRequest;
import com.onetool.server.api.qna.dto.request.PostQnaReplyRequest;
import com.onetool.server.api.qna.service.QnaReplyService;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Business
@RequiredArgsConstructor
public class QnaReplyBusiness {

    private final QnaReplyService qnaReplyService;
    private final QnaReplyConverter qnaReplyConverter;

    @Transactional
    public void postQnaReply(Principal principal, Long qnaId, PostQnaReplyRequest request) {
        Member member = qnaReplyService.findMember(principal);
        QnaBoard qnaBoard = qnaReplyService.findQnaBoard(qnaId);
        QnaReply qnaReply = qnaReplyConverter.fromRequestTooQnaReply(request);

        qnaReplyService.saveReply(member,qnaBoard,qnaReply);

    }

    @Transactional
    public void deleteQnaReply(Principal principal, Long qnaId, ModifyQnaReplyRequest request) {
        Member member = qnaReplyService.findMember(principal);
        QnaBoard qnaBoard = qnaReplyService.findQnaBoard(qnaId);
        QnaReply qnaReply = qnaReplyService.findQnaReply(request.replyId());

        qnaReplyService.deleteReply(member, qnaBoard, qnaReply);
    }
}
