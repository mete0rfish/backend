package com.onetool.server.api.qna.service;

import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.request.QnaBoardRequest;
import com.onetool.server.global.exception.ErrorException;
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
public class QnaBoardServiceImpl implements QnaBoardService {

    private final QnaBoardRepository qnaBoardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<QnaBoard> findAllQnaBoardList() {
        List<QnaBoard> qnaBoards = qnaBoardRepository
                .findAllQnaBoardsOrderedByCreatedAt();

        hasErrorWithNoContent(qnaBoards);
        //TODO : 페이징 관련
        return qnaBoards;
    }

    @Transactional(readOnly = true)
    public QnaBoard findQnaBoard(Long qnaId) {
        return qnaBoardRepository
                .findByIdWithReplies(qnaId)
                .orElseThrow(() -> new BaseException(NO_QNA_CONTENT));
    }

    @Transactional(readOnly = true)
    public Member findMember(Principal principal) {
        return memberRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    @Transactional
    public void postQna(Member member, QnaBoard qnaBoard) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new ErrorException("qnaBoard는 null입니다. 함수명 : postQna"))
                .assignMember(member);
        qnaBoardRepository.save(qnaBoard);
    }

    @Transactional
    public void deleteQna(QnaBoard qnaBoard, Member member) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new ErrorException("qnaBoard는 null입니다. 함수명 : postQna"))
                .unassignMember(member);

        qnaBoardRepository.delete(qnaBoard);
    }

    @Transactional
    public void updateQna(QnaBoard qnaBoard) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new ErrorException("qnaBoard는 null입니다. 함수명 : updateQna"));
    }


    public void hasErrorWithNoContent(List<QnaBoard> data) {
        if (data.isEmpty())
            throw new BaseException(NO_QNA_CONTENT);
    }
}