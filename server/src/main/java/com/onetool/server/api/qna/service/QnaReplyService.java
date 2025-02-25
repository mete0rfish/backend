package com.onetool.server.api.qna.service;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.repository.MemberRepository;
import com.onetool.server.api.qna.QnaBoard;
import com.onetool.server.api.qna.QnaReply;
import com.onetool.server.api.qna.repository.QnaBoardRepository;
import com.onetool.server.api.qna.repository.QnaReplyRepository;
import com.onetool.server.global.exception.QnaNullPointException;
import com.onetool.server.global.exception.base.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.onetool.server.global.exception.codes.ErrorCode.NON_EXIST_USER;
import static com.onetool.server.global.exception.codes.ErrorCode.NO_QNA_REPLY;

@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final MemberRepository memberRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final QnaReplyRepository qnaReplyRepository;

    public void saveReply(Member member, QnaBoard qnaBoard, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);

        qnaReply.assignBoard(qnaBoard);
        qnaReply.assignMember(member);
        qnaReplyRepository.save(qnaReply);
    }

    public void deleteReply(Member member, QnaBoard qnaBoard, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);

        qnaReply.validateMemberWithReply(member);
        qnaReply.unassignMemberAndQnaBoard();
    }

    public void updateReply(Member member, String content, QnaReply qnaReply) {

        validateQnaReplyIsNull(qnaReply);
        qnaReply.validateMemberWithReply(member);

        qnaReply.updateReply(content);
    }

    public QnaReply findQnaReply(Long id) {
        return qnaReplyRepository
                .findByIdWithBoardAndMember(id)
                .orElseThrow(() -> new BaseException(NO_QNA_REPLY));
    }

    public Member findMember(Principal principal) {
        return memberRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    public QnaBoard findQnaBoard(Long id) {
        return qnaBoardRepository
                .findByIdWithReplies(id)
                .orElseThrow(() -> new BaseException(NON_EXIST_USER));
    }

    private void validateQnaReplyIsNull(QnaReply qnaReply) {
        if (qnaReply == null) {
            throw new QnaNullPointException("qnaReply는 null입니다. 함수명: saveReply");
        }
    }
}