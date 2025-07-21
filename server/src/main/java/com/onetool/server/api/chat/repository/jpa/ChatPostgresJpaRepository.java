package com.onetool.server.api.chat.repository.jpa;

import com.onetool.server.api.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatPostgresJpaRepository extends JpaRepository<ChatMessage, Long> {

    ChatMessage save(ChatMessage chatMessage);

    @Modifying
    @Query("DELETE FROM ChatMessage c WHERE c.createdAt < :cutoff")
    void deleteByCreatedAtBefore(@Param("cutoff") LocalDateTime cutoff);

    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(String roomId);
}