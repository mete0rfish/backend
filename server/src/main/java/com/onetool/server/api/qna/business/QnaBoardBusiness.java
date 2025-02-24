package com.onetool.server.api.qna.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.converter.QnaBoardConverter;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.response.QnaBoardBriefResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardDetailResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardResponse;
import com.onetool.server.api.qna.dto.response.QnaReplyResponse;
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

    public List<QnaBoardBriefResponse> getQnaBoardBriefList(){

        List<QnaBoard> qnaBoards = qnaBoardService.findAllQnaBoardList();
        return qnaBoardConverter.fromQnaBoardListToBriefResponseList(qnaBoards);
    }

    @Transactional
    public void postQnaBoard(Principal principal, PostQnaBoardRequest request){

        Member member = qnaBoardService.findMember(principal);
        QnaBoard qnaBoard = qnaBoardConverter.fromRequestToQnaBoard(request);
        qnaBoardService.postQna(member,qnaBoard);
    }

    @Transactional
    public QnaBoardDetailResponse getOneQnaBoardDetail(Principal principal, Long qnaId){

        Member member = qnaBoardService.findMember(principal);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoard(qnaId);
        boolean authorization = qnaBoard.validateMemberCanModify(member);

        return qnaBoardConverter.fromQnaBoardToDetailResponse(qnaBoard,authorization);
    }

    @Transactional
    public void deleteQnaBoard(Principal principal, Long qnaId){

        Member member = qnaBoardService.findMember(principal);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoard(qnaId);
        qnaBoard.validateMemberCanModify(member);

        qnaBoardService.deleteQna(qnaBoard ,member);
    }

    @Transactional
    public void updateQnaBoard(Principal principal, Long qnaId, PostQnaBoardRequest request) {

        Member member = qnaBoardService.findMember(principal);
        QnaBoard qnaBoard = qnaBoardService.findQnaBoard(qnaId);
        qnaBoard.validateMemberCanModify(member);
        qnaBoard.updateQnaBoard(request); //todo 업데이트 하는 과정을 converter의 역할로 부여해야 할까요??
        //todo service에 부여화는게 나을까요??
        qnaBoardService.updateQna(qnaBoard);
    }

}
