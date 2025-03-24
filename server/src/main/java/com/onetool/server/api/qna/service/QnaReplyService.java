package com.onetool.server.api.qna.service;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.QnaReply;
import com.onetool.server.api.qna.repository.QnaReplyRepository;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.QnaErrorCode;
import com.onetool.server.global.new_exception.exception.error.ServerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;

    public QnaReply fetchWithBoardAndMember(Long id) {
        return qnaReplyRepository
                .findByIdWithBoardAndMember(id)
                .orElseThrow(() ->new ApiException(QnaErrorCode.NOT_FOUND_ERROR));
    }

    public void saveQnaReply(Member member, QnaBoard qnaBoard, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);

        qnaReply.assignBoard(qnaBoard);
        qnaReply.assignMember(member);
        qnaReplyRepository.save(qnaReply);
    }

    public void deleteQnaReply(Member member, QnaBoard qnaBoard, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);

        qnaReply.validateMemberCanModifyAndDelete(member);
        qnaReply.unassignMemberAndQnaBoard();
        qnaReplyRepository.deleteById(qnaReply.getId());
    }

    public void updateQnaReply(Member member, String content, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);
        qnaReply.validateMemberCanModifyAndDelete(member);

        qnaReply.updateReply(content);
    }

    private void validateQnaReplyIsNull(QnaReply qnaReply) {
        if (qnaReply == null) {
            throw new ApiException(QnaErrorCode.NULL_POINT_ERROR);
        }
    }
}