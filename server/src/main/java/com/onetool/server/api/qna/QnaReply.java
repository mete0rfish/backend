package com.onetool.server.api.qna;

import com.onetool.server.api.qna.dto.request.PostQnaReplyRequest;
import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.global.exception.UnAvailableModifyeException;
import com.onetool.server.global.exception.base.BaseException;
import com.onetool.server.global.exception.codes.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.onetool.server.global.exception.codes.ErrorCode.UNAVAILABLE_TO_MODIFY;

@Entity
@Table(name = "qna_reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaReply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply_content") @NotNull
    @Size(min = 2, max = 10, message = "내용은 2 ~ 100자 이여야 합니다.")
    private String content;

    @Column(name = "reply_writer") @NotNull
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_id")
    private QnaBoard qnaBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_reply_id")
    private Member member;

    @Builder
    private QnaReply(String content) {
        this.content = content;
    }

    public  QnaReply createReply(PostQnaReplyRequest request){
        return QnaReply.builder()
                .content(request.content())
                .build();
    }

    public void updateReply(String content){
        this.content = content;
    }

    public void validateMemberCanModifyAndDelete(Member member){
        if(!this.getMember().getEmail().equals(member.getEmail())){
            throw new UnAvailableModifyeException("해당 유저는 수정 및 삭제 권한이 없습니다.");
        }
    }

    //---------------연관관계 매핑---------------

    public void assignBoard(QnaBoard qnaBoard){
        this.qnaBoard = qnaBoard;
        qnaBoard.getQnaReplies().add(this);
    }

    public void assignMember(Member member){
        this.member = member;
        this.writer = member.getName();
        member.getQnaReplies().add(this);
    }

    public void unassignMemberAndQnaBoard(){
        member.getQnaReplies().remove(this);
        qnaBoard.getQnaReplies().remove(this);
        this.member = null;
        this.qnaBoard = null;
    }

}