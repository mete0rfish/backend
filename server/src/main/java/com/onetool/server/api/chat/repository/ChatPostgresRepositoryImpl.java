package com.onetool.server.api.chat.repository;

import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.repository.jpa.ChatPostgresJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("chatPostgresRepositoryImpl")
@Profile("postgres")
@RequiredArgsConstructor
public class ChatPostgresRepositoryImpl implements ChatRepository {

    private final ChatPostgresJpaRepository delegate;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return delegate.save(chatMessage);
    }

    @Override
    public void deleteExpiredChatMessagesBefore(LocalDateTime cutoff) {
        delegate.deleteByCreatedAtBefore(cutoff);
    }

    @Override
    public List<ChatMessage> findLatestMessages(String roomId) {
        return delegate.findByRoomIdOrderByCreatedAtDesc(roomId);
    }
} 