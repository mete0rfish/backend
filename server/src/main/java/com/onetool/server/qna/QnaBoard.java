package com.onetool.server.qna;

import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "qna_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "qna_board_title") @NotNull
    @Size(min = 2, max = 30, message = "제목은 2 ~ 30자 이여야 합니다.")
    private String title;
    @Column(name = "qna_board_content")
    @Size(min = 2, max = 100, message = "내용은 2 ~ 100자 이여야 합니다.")
    private String content;
    @OneToMany(mappedBy = "qna_board")
    private List<QnaReply> qnaReplies;

    @Builder
    private QnaBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // TODO builder패턴 완성하기
}
