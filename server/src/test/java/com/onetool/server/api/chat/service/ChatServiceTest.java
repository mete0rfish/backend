package com.onetool.server.api.chat.service;

import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.MessageType;
import com.onetool.server.api.chat.dto.ChatMessageResponse;
import com.onetool.server.api.chat.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatServiceTest {

    private static final String ROOM_ID = "dwadwadwadaw";
    private static final String MESSAGE = "메시지 내용~~~~~~~";
    private static final String SENDER = "ㅇㅇㅇㅇㅇㅇㅇㅇ";

    @Autowired
    private  ChatRepository chatRepository;
    @Autowired
    private  ChatService chatService;

    @BeforeEach
    void setUp() {
        initChatMessages();
    }

    @Test
    void findChatMessages() {
        // given

        // when
        List<ChatMessageResponse> response = chatService.findChatMessages(ROOM_ID);

        // then
        assertThat(response.size()).isEqualTo(3);
        for (ChatMessageResponse chatMessageResponse : response) {
            assertThat(chatMessageResponse.message()).isEqualTo(MESSAGE);
            assertThat(chatMessageResponse.sender()).isEqualTo(SENDER);
        }
    }

    private void initChatMessages() {
        chatRepository.save(createChatMessage(MessageType.ENTER));
        chatRepository.save(createChatMessage(MessageType.TALK));
        chatRepository.save(createChatMessage(MessageType.QUIT));
    }

    private ChatMessage createChatMessage(MessageType type) {
        return ChatMessage.builder()
                .message(MESSAGE)
                .type(type)
                .sender(SENDER)
                .roomId(ROOM_ID)
                .build();
    }
}