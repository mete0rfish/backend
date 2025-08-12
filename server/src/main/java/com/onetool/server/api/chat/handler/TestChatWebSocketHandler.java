package com.onetool.server.api.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetool.server.api.chat.domain.ChatMessageQueue;
import com.onetool.server.api.chat.service.ChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class TestChatWebSocketHandler extends ChatWebSocketHandler {

    public TestChatWebSocketHandler(ObjectMapper objectMapper, ChatService chatService, ChatMessageQueue chatMessageQueue) {
        super(objectMapper, chatService, chatMessageQueue);
    }

    public void handleTextMessageForTest(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }
}
