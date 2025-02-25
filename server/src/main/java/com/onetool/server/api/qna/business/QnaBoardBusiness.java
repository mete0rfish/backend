package com.onetool.server.api.qna.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.converter.QnaBoardConverter;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.response.QnaBoardBriefResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardDetailResponse;
import com.onetool.server.api.qna.service.QnaBoardService;
import com.onetool.server.global.annotation.Business;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

@Business
@RequiredArgsConstructor
public class QnaBoardBusiness {

    private final QnaBoardService qnaBoardService;
    private final QnaBoardConverter qnaBoardConverter;

    public List<QnaBoardBriefResponse> getQnaBoardBriefList() {

        List<QnaBoard> qnaBoards = qnaBoardService.getAllQnaBoards();
        return qnaBoardConverter.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional
    public void createQnaBoard(Principal principal, PostQnaBoardRequest request) {

        Member member = qnaBoardService.getMemberByPrincipal(principal);
        QnaBoard qnaBoard = qnaBoardConverter.fromRequestToQnaBoard(request);
        qnaBoardService.saveQnaBoard(member, qnaBoard);
    }

    @Transactional
    public QnaBoardDetailResponse getQnaBoardDetail(Principal principal, Long qnaId) {

        Member member = qnaBoardService.getMemberByPrincipal(principal);
        QnaBoard qnaBoard = qnaBoardService.getQnaBoardById(qnaId);
        boolean authorization = qnaBoard.validateMemberCanModify(member);

        return qnaBoardConverter.fromQnaBoardToDetailResponse(qnaBoard, authorization);
    }

    @Transactional
    public void removeQnaBoard(Principal principal, Long qnaId) {

        Member member = qnaBoardService.getMemberByPrincipal(principal);
        QnaBoard qnaBoard = qnaBoardService.getQnaBoardById(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.removeQnaBoard(qnaBoard, member);
    }

    @Transactional
    public void editQnaBoard(Principal principal, Long qnaId, PostQnaBoardRequest request) {

        Member member = qnaBoardService.getMemberByPrincipal(principal);
        QnaBoard qnaBoard = qnaBoardService.getQnaBoardById(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.updateQnaBoard(qnaBoard, request);
    }

}
