package com.onetool.server.api.qna.service;

import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.global.exception.QnaNullPointException;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.repository.QnaBoardRepository;
import com.onetool.server.global.exception.codes.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.onetool.server.global.new_exception.exception.error.QnaErrorCode.NO_QNA_CONTENT;


@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardRepository qnaBoardRepository;

    @Transactional(readOnly = true)
    public List<QnaBoard> findAllQnaBoards() {
        List<QnaBoard> qnaBoards = qnaBoardRepository
                .findAllQnaBoardsOrderedByCreatedAt();

        hasErrorWithNoContent(qnaBoards);
        //TODO : 페이징 관련
        return qnaBoards;
    }

    @Transactional(readOnly = true)
    public QnaBoard findQnaBoardById(Long qnaId) {
        return qnaBoardRepository
                .findByIdWithReplies(qnaId)
                .orElseThrow(() -> new BaseException(ErrorCode.NO_QNA_REPLY));
    }

    @Transactional
    public void saveQnaBoard(Member member, QnaBoard qnaBoard) {
        Optional.ofNullable(qnaBoard)
                .orElseThrow(() -> new QnaNullPointException("qnaBoard는 null입니다. 함수명 : saveQnaBoard"))
                .assignMember(member);
        qnaBoardRepository.save(qnaBoard);
    }

    @Transactional
    public void deleteQnaBoard(QnaBoard qnaBoard, Member member) {
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
            throw new BaseException(ErrorCode.NO_QNA_REPLY);
    }
}