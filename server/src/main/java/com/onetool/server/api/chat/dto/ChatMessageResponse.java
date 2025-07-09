package com.onetool.server.api.chat.dto;

import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageResponse(
        MessageType type,
        String sender,
        String message,
        LocalDateTime createdAt
) {

    public static ChatMessageResponse from(ChatMessage message) {
        return ChatMessageResponse.builder()
                .type(message.getType())
                .sender(message.getSender())
                .message(message.getMessage())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
