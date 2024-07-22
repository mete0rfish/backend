package com.onetool.server.qna;

import com.onetool.server.global.entity.BaseEntity;
import com.onetool.server.member.Member;
import com.onetool.server.qna.dto.request.QnaRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "qna_board", indexes = @Index(name = "idx_qna", columnList = "qna_board_title, member_id, created_at"))
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
    @OneToMany(mappedBy = "qnaBoard")
    private List<QnaReply> qnaReplies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private QnaBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static QnaBoard createQnaBoard(QnaRequest.PostQnaBoard request){
        return new QnaBoard(request.title(),
                request.content());

    }

    public void post(Member member){
        this.member = member;
        member.getQnaBoards().add(this);
    }
    // TODO builder패턴 완성하기
}
