package com.onetool.server.api.qna.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.response.QnaBoardBriefResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardDetailResponse;
import com.onetool.server.api.qna.service.QnaBoardService;
import com.onetool.server.global.annotation.Business;
import com.onetool.server.global.auth.MemberAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Business
@RequiredArgsConstructor
public class QnaBoardBusiness {

    private final QnaBoardService qnaBoardService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public List<QnaBoardBriefResponse> getQnaBoardBriefList() {
        List<QnaBoard> qnaBoards = qnaBoardService.findAll();
        return QnaBoardBriefResponse.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional(readOnly = true)
    public List<QnaBoardBriefResponse> getMyQna(Long id) {
        memberService.validateExistId(id);
        List<QnaBoard> qnaBoards = qnaBoardService.findAll(id);
        return QnaBoardBriefResponse.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional
    public void createQnaBoard(String email, PostQnaBoardRequest request) {
        Member member = memberService.findOne(email);
        QnaBoard qnaBoard = request.toQnaBoard();
        qnaBoardService.saveQnaBoard(member, qnaBoard);
    }

    @Transactional
    public QnaBoardDetailResponse getQnaBoardDetail(String email, Long qnaId) {
        Member member = memberService.findOne(email);
        QnaBoard qnaBoard = qnaBoardService.fetchWithQnaReply(qnaId);
        boolean authorization = qnaBoard.isMyQnaBoard(member);

        return QnaBoardDetailResponse.fromQnaBoardToDetailResponse(qnaBoard, authorization);
    }

    @Transactional
    public void removeQnaBoard(String email, Long qnaId) {
        Member member = memberService.findOne(email);
        QnaBoard qnaBoard = qnaBoardService.fetchWithQnaReply(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.deleteQnaBoard(qnaBoard, member);
    }

    @Transactional
    public void editQnaBoard(String email, Long qnaId, PostQnaBoardRequest request) {
        Member member = memberService.findOne(email);
        QnaBoard qnaBoard = qnaBoardService.fetchWithQnaReply(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.updateQnaBoard(qnaBoard, request);
    }
}
