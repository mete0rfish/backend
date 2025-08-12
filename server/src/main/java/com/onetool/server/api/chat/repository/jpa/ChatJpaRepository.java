package com.onetool.server.api.chat.repository.jpa;

import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.repository.ChatRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatJpaRepository extends JpaRepository<ChatMessage, Long> {

    ChatMessage save(ChatMessage chatMessage);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM chat_message WHERE created_at < :cutoff", nativeQuery = true)
    void deleteExpiredChatMessagesBefore(@Param("cutoff") LocalDateTime cutoff);

    @Query("SELECT c FROM ChatMessage c WHERE c.roomId = :roomId ORDER BY c.createdAt DESC")
    List<ChatMessage> findLatestMessages(@Param("roomId") String roomId);
}
