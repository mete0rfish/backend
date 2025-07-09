package com.onetool.server.api.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.ChatRoom;
import com.onetool.server.api.chat.dto.ChatMessageResponse;
import com.onetool.server.api.chat.repository.ChatRepository;
import groovy.util.logging.Slf4j;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private Map<String, ChatRoom> chatRooms;
    private final ChatRepository chatRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    @Transactional
    public Long saveTextMessage(ChatMessage chatMessage) {
        ChatMessage savedChatMessage = chatRepository.save(chatMessage);
        return savedChatMessage.getId();
    }

    @Transactional
    public void deleteExpiredChatMessages() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(3);
        chatRepository.deleteExpiredChatMessagesBefore(cutoff);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> findChatMessages(final String roomId) {
        return chatRepository.findLatestMessages(roomId)
                .stream().map(ChatMessageResponse::from)
                .toList();
    }
}