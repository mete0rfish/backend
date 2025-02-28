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
        List<QnaBoard> qnaBoards = qnaBoardService.findAllQnaBoards();
        return QnaBoardBriefResponse.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional(readOnly = true)
    public List<QnaBoardBriefResponse> getMyQna(MemberAuthContext context) {
        memberService.validateExistId(context.getId());
        List<QnaBoard> qnaBoards = qnaBoardService.findAllByMemberId(context.getId());
        return QnaBoardBriefResponse.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional
    public void createQnaBoard(String email, PostQnaBoardRequest request) {
        Member member = memberService.findByEmail(email);
        QnaBoard qnaBoard = request.toQnaBoard();
        qnaBoardService.saveQnaBoard(member, qnaBoard);
    }

    @Transactional
    public QnaBoardDetailResponse getQnaBoardDetail(String email, Long qnaId) {
        Member member = memberService.findByEmail(email);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoardById(qnaId);
        boolean authorization = qnaBoard.isMyQnaBoard(member);

        return QnaBoardDetailResponse.fromQnaBoardToDetailResponse(qnaBoard, authorization);
    }

    @Transactional
    public void removeQnaBoard(String email, Long qnaId) {
        Member member = memberService.findByEmail(email);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoardById(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.deleteQnaBoard(qnaBoard, member);
    }

    @Transactional
    public void editQnaBoard(String email, Long qnaId, PostQnaBoardRequest request) {
        Member member = memberService.findByEmail(email);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoardById(qnaId);
        qnaBoard.validateMemberCanRemoveOrUpdate(member);

        qnaBoardService.updateQnaBoard(qnaBoard, request);
    }
}
