package com.onetool.server.api.chat.repository.mongo;

import com.onetool.server.api.chat.domain.ChatMessageMongo;
import com.onetool.server.api.chat.repository.ChatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMongoRepository extends MongoRepository<ChatMessageMongo, String> {
    ChatMessageMongo save(ChatMessageMongo chatMessage);
    void deleteByCreatedAtBefore(LocalDateTime cutoff);
    List<ChatMessageMongo> findByRoomIdOrderByCreatedAtDesc(Pageable pageable, String roomId);
}
