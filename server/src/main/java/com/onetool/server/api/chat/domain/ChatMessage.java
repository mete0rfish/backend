package com.onetool.server.api.chat.domain;

import com.onetool.server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "CHAT_MESSAGE_SEQ_GENERATOR",
        sequenceName = "CHAT_MESSAGE_SEQ",
        initialValue = 1, allocationSize = 100
)
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CHAT_MESSAGE_SEQ_GENERATOR"
    )
    private Long id;
    private boolean persisted;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String roomId;
    private String sender;
    private String message;
}
