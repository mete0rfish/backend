package com.onetool.server.qna.service;

import com.onetool.server.global.handler.MyExceptionHandler;
import com.onetool.server.member.Member;
import com.onetool.server.member.MemberRepository;
import com.onetool.server.qna.QnaBoard;
import com.onetool.server.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.onetool.server.global.exception.codes.ErrorCode.*;
import static com.onetool.server.qna.dto.response.QnaResponse.*;
import static com.onetool.server.qna.dto.request.QnaRequest.*;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    public List<QnaBoardBriefResponse> getQnaBoard() {
        List<QnaBoard> qnaBoards = qnaRepository
                .findAllQnaBoardsOrderedByCreatedDate();
        hasErrorWithNoContent(qnaBoards);

        //TODO : 페이징 관련 최적화 하기
        return qnaBoards
                .stream()
                .map(QnaBoardBriefResponse::brief)
                .toList();
    }

    public void postQna(Principal principal, PostQnaBoard request) {
        Member member = memberRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new MyExceptionHandler(EMAIL_NOT_FOUND));
        QnaBoard qnaBoard = QnaBoard.createQnaBoard(request);
        qnaBoard.post(member);
        qnaRepository.save(qnaBoard);
    }

    public QnaBoardDetailResponse getQnaBoardDetails(Long boardId) {
        QnaBoard qnaBoard = qnaRepository.findById(boardId).orElseThrow(() -> new MyExceptionHandler(NO_QNA));
        return QnaBoardDetailResponse.details(qnaBoard);
    }

    public void hasErrorWithNoContent(List<QnaBoard> data) {
        if(data.isEmpty())
            throw new MyExceptionHandler(NO_QNA);
    }
}
