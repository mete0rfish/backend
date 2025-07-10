package com.onetool.server.api.chat.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.ChatRoom;
import com.onetool.server.api.chat.domain.MessageType;
import com.onetool.server.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage");
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        Set<WebSocketSession> sessions = room.getSessions();

        if (chatMessage.getType().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else if (chatMessage.getType().equals(MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            sendToEachSocket(sessions,new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else {
            sendToEachSocket(sessions,message);
        }

        Long chatId = chatService.saveTextMessage(chatMessage);
        log.info("저장 완료 chat Id : {}",chatId);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("RoomId: " + ChatRoom.roomId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ChatRoom room = chatService.findRoomById(ChatRoom.roomId);
        Set<WebSocketSession> sessions = room.getSessions();
        sessions.remove(session);
    }

    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message){
        sessions.parallelStream().forEach(roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }
}
