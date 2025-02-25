package com.onetool.server.api.qna.service;

import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.global.exception.QnaNullPointException;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.repository.MemberRepository;
import com.onetool.server.api.qna.repository.QnaBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.onetool.server.global.exception.codes.ErrorCode.NON_EXIST_USER;
import static com.onetool.server.global.exception.codes.ErrorCode.NO_QNA_CONTENT;

@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardRepository qnaBoardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<QnaBoard> getAllQnaBoards() {
        List<QnaBoard> qnaBoards = qnaBoardRepository
                .findAllQnaBoardsOrderedByCreatedAt();

        hasErrorWithNoContent(qnaBoards);
        //TODO : 페이징 관련
        return qnaBoards;
    }

    @Transactional(readOnly = true)
    public QnaBoard getQnaBoardById(Long qnaId) {
        return qnaBoardRepository
                .findByIdWithReplies(qnaId)
                .orElseThrow(() -> new BaseException(NO_QNA_CONTENT));
    }

    @Transactional(readOnly = true)
    public Member getMemberByPrincipal(Principal principal) {
        return memberRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    @Transactional
    public void saveQnaBoard(Member member, QnaBoard qnaBoard) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new QnaNullPointException("qnaBoard는 null입니다. 함수명 : saveQnaBoard"))
                .assignMember(member);
        qnaBoardRepository.save(qnaBoard);
    }

    @Transactional
    public void removeQnaBoard(QnaBoard qnaBoard, Member member) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new QnaNullPointException("qnaBoard는 null입니다. 함수명 : removeQnaBoard"))
                .unassignMember(member);

        qnaBoardRepository.delete(qnaBoard);
    }

    @Transactional
    public void updateQnaBoard(QnaBoard qnaBoard, PostQnaBoardRequest request) {
        if (qnaBoard == null) {
            throw new QnaNullPointException("qnaBoard는 null입니다. 함수명 : updateQnaBoard");
        }

        qnaBoard.update(request.title(), request.content());
    }


    public void hasErrorWithNoContent(List<QnaBoard> data) {
        if(data.isEmpty())
            throw new BaseException(NO_QNA_CONTENT);
    }
}