package com.onetool.server.qna;

import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qna_reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaReply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "reply_content") @NotNull
    @Size(min = 2, max = 10, message = "내용은 2 ~ 100자 이여야 합니다.")
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnaBoard qnaBoard;
  
  
    @Builder
    public QnaReply(String content) {
        this.content = content;
    }

    //TODO builder 패턴 완성하기
}