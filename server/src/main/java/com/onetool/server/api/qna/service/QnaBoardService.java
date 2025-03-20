package com.onetool.server.api.qna.service;

import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.repository.QnaBoardRepository;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.QnaErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardRepository qnaBoardRepository;

    @Transactional(readOnly = true)
    public List<QnaBoard> findAll() {
        List<QnaBoard> qnaBoards = qnaBoardRepository
                .findAllQnaBoardsOrderedByCreatedAt();
        hasErrorWithNoContent(qnaBoards);
        return qnaBoards;
    }

    @Transactional(readOnly = true)
    public List<QnaBoard> findAll(Long memberId) {
        return qnaBoardRepository.findByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public QnaBoard fetchWithQnaReply(Long qnaId) {
        return qnaBoardRepository
                .findByIdWithReplies(qnaId)
                .orElseThrow(() -> new ApiException(QnaErrorCode.NOT_FOUND_ERROR));
    }

    @Transactional
    public void saveQnaBoard(Member member, QnaBoard qnaBoard) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() ->new ApiException(QnaErrorCode.NULL_POINT_ERROR,"qnaBoard가 NULL입니다."))
                .assignMember(member);
        qnaBoardRepository.save(qnaBoard);
    }

    @Transactional
    public void deleteQnaBoard(QnaBoard qnaBoard, Member member) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new ApiException(QnaErrorCode.NULL_POINT_ERROR,"qnaBoard가 NULL입니다."))
                .unassignMember(member);

        qnaBoardRepository.delete(qnaBoard);
    }

    @Transactional
    public void updateQnaBoard(QnaBoard qnaBoard, PostQnaBoardRequest request) {
        if (qnaBoard == null) {
            throw new ApiException(QnaErrorCode.NULL_POINT_ERROR,"qnaBoard가 NULL입니다.");
        }

        qnaBoard.update(request.title(), request.content());
    }

    private void hasErrorWithNoContent(List<QnaBoard> data) {
        if(data.isEmpty())
            throw new ApiException(QnaErrorCode.NOT_FOUND_ERROR);
    }
}