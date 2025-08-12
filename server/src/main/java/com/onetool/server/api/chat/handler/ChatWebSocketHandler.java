package com.onetool.server.api.chat.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetool.server.api.chat.domain.ChatMessage;
import com.onetool.server.api.chat.domain.ChatMessageQueue;
import com.onetool.server.api.chat.domain.ChatRoom;
import com.onetool.server.api.chat.domain.MessageType;
import com.onetool.server.api.chat.service.ChatService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final ChatMessageQueue chatMessageQueue;
//    private final ScheduledExecutorService scheduler;

    public ChatWebSocketHandler(ObjectMapper objectMapper, ChatService chatService, ChatMessageQueue chatMessageQueue) {
        this.objectMapper = objectMapper;
        this.chatService = chatService;
        this.chatMessageQueue = chatMessageQueue;

//        this.scheduler = Executors.newSingleThreadScheduledExecutor();
//        // 주기적으로 메시지 저장 작업 실행
//        this.scheduler.scheduleAtFixedRate(
//                this::processMessageQueue,
//                0,
//                5,
//                TimeUnit.SECONDS
//        );
    }

//    @PreDestroy
//    public void cleanup() {
//        scheduler.shutdown();
//        // 종료 전 남은 메시지 처리
//        processMessageQueue();
//    }

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

        chatMessageQueue.addMessage(chatMessage);
        // 메시지가 충분히 쌓였다면 저장 프로세스 실행
        if (chatMessageQueue.hasEnoughMessages()) {
            log.info("MessageQueue Size: {}", chatMessageQueue.getQueueSize());
            processMessageQueue();
        }

//        Long chatId = chatService.saveTextMessage(chatMessage);
//        log.info("저장 완료 chat Id : {}",chatId);
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

    private void processMessageQueue() {
        List<ChatMessage> unpersistedMessages = chatMessageQueue.getUnpersistedMessages();
        if (unpersistedMessages.isEmpty()) {
            return;
        }

        try {
            // 배치 사이즈만큼만 처리
            List<ChatMessage> messagesToPersist = unpersistedMessages.stream()
                    .limit(ChatMessageQueue.getBatchSize())
                    .collect(Collectors.toList());

            // DB에 저장
            Integer messageSize = chatService.saveAllTextMessage(messagesToPersist);
            log.info("Messages in Queue are saved. size: {}", messageSize);

            // 저장된 메시지 표시 및 큐에서 제거
            chatMessageQueue.markMessagesAsPersisted(messagesToPersist);

        } catch (Exception e) {
            log.error("배치 메시지 저장 중 오류 발생", e);
        }
    }

}
