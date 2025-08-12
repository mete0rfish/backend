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
public class ChatMessage extends BaseEntity {

    // ✅ PostgreSQL(MySQL 포함)용 ID (Primary Key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean persisted;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String roomId;
    private String sender;
    private String message;
}
