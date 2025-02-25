package com.onetool.server.api.qna.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.QnaReply;
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
    public void createQnaReply(Principal principal, Long qnaId, PostQnaReplyRequest request) {
        Member member = qnaReplyService.getMemberByIdFromQnaReply(principal);
        QnaBoard qnaBoard = qnaReplyService.getQnaBoardByIdFromQnaReply(qnaId);
        QnaReply qnaReply = qnaReplyConverter.fromRequestTooQnaReply(request);

        qnaReplyService.saveQnaReply(member,qnaBoard,qnaReply);

    }

    @Transactional
    public void removeQnaReply(Principal principal, Long qnaId, ModifyQnaReplyRequest request) {
        Member member = qnaReplyService.getMemberByIdFromQnaReply(principal);
        QnaBoard qnaBoard = qnaReplyService.getQnaBoardByIdFromQnaReply(qnaId);
        QnaReply qnaReply = qnaReplyService.getQnaReplyById(request.replyId());

        qnaReplyService.deleteQnaReply(member, qnaBoard, qnaReply);
    }

    public void updateQnaReply(Principal principal, Long qnaId, ModifyQnaReplyRequest request) {
        Member member = qnaReplyService.getMemberByIdFromQnaReply(principal);
        QnaReply qnaReply = qnaReplyService.getQnaReplyById(request.replyId());

        qnaReplyService.updateQnaReply(member,request.content(), qnaReply);
    }
}
