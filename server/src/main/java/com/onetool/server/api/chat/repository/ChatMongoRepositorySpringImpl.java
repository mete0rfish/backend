package com.onetool.server.api.chat.repository;

import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.ChatMessageMongo;
import com.onetool.server.api.chat.repository.mongo.ChatMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class ChatMongoRepositorySpringImpl implements ChatRepository {

    private final ChatMongoRepository delegate;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        // ChatMessage를 ChatMessageMongo로 변환
        ChatMessageMongo mongoMessage = ChatMessageMongo.builder()
                .type(chatMessage.getType())
                .roomId(chatMessage.getRoomId())
                .sender(chatMessage.getSender())
                .message(chatMessage.getMessage())
                .build();
        
        ChatMessageMongo savedMongoMessage = delegate.save(mongoMessage);
        
        // ChatMessageMongo를 ChatMessage로 변환하여 반환
        return ChatMessage.builder()
                .id(null)
                .type(savedMongoMessage.getType())
                .roomId(savedMongoMessage.getRoomId())
                .sender(savedMongoMessage.getSender())
                .message(savedMongoMessage.getMessage())
                .build();
    }

    @Override
    public void deleteExpiredChatMessagesBefore(LocalDateTime cutoff) {
        delegate.deleteByCreatedAtBefore(cutoff);
    }

    @Override
    public List<ChatMessage> findLatestMessages(String roomId) {
        List<ChatMessageMongo> mongoMessages = delegate.findByRoomIdOrderByCreatedAtDesc(roomId);
        
        // ChatMessageMongo 리스트를 ChatMessage 리스트로 변환
        return mongoMessages.stream()
                .map(mongoMessage -> ChatMessage.builder()
                        .id(Long.valueOf(mongoMessage.getMongoId()))
                        .type(mongoMessage.getType())
                        .roomId(mongoMessage.getRoomId())
                        .sender(mongoMessage.getSender())
                        .message(mongoMessage.getMessage())
                        .build())
                .toList();
    }
} 