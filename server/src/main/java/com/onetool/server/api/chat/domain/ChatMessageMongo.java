package com.onetool.server.api.chat.domain;

import com.onetool.server.global.entity.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_message") // ✅ MongoDB에서 사용
public class ChatMessageMongo extends BaseEntity {

    // ✅ MongoDB용 ID (_id 필드)
    @MongoId
    private String mongoId;

    private MessageType type;

    private String roomId;
    private String sender;
    private String message;
} 