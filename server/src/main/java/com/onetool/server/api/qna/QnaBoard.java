package com.onetool.server.api.qna;

import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.api.member.domain.Member;
import com.onetool.server.global.exception.UnAvailableModifyeException;
import com.onetool.server.global.new_exception.exception.ApiException;
import com.onetool.server.global.new_exception.exception.error.QnaErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "qna_board",
        indexes = @Index(name = "idx_qna", columnList = "qna_board_title"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qna_board_title") @NotNull
    @Size(min = 2, max = 30, message = "제목은 2 ~ 30자 이여야 합니다.")
    private String title;

    @Column(name = "qna_board_content")
    @Size(min = 2, max = 100, message = "내용은 2 ~ 100자 이여야 합니다.")
    private String content;

    @Column(name = "qna_board_views")
    private Long views;

    @OneToMany(mappedBy = "qnaBoard", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<QnaReply> qnaReplies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public QnaBoard(String title, String content) {
        this.title = title;
        this.content = content;
        this.views = 0L;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //todo 예외처리 클래스 새로 생성할 예정 .......
    public boolean isMyQnaBoard(Member member) {
        return this.member.getEmail().equals(member.getEmail());
    }

    public void validateMemberCanRemoveOrUpdate(Member member) {
        if(!this.member.getEmail().equals(member.getEmail())){
            throw new ApiException(QnaErrorCode.UNAVAILABLE_TO_MODIFY,"해당 유저는 삭제 및 수정 권한이 없습니다.");
        }
    }

    //연관관계 맺고 끊는 함수들
    public void assignMember(Member member){
        this.member = member;
        member.getQnaBoards().add(this);
    }

    public void unassignMember(Member member){
        member.getQnaBoards().remove(this);
        this.member = null;
    }
}