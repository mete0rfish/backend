package com.onetool.server.api.chat.domain;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ChatMessageQueue {
    private final Queue<ChatMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private static final int BATCH_SIZE = 10;

    public void addMessage(ChatMessage message) {
        messageQueue.offer(message);
    }

    public List<ChatMessage> getUnpersistedMessages() {
        return messageQueue.stream()
                .filter(message -> !message.isPersisted())
                .collect(Collectors.toList());
    }

    public List<ChatMessage> getQueuedMessages(String roomId) {
        return messageQueue.stream()
                .filter(message -> message.getRoomId().equals(roomId))
                .collect(Collectors.toList());
    }

    public void markMessagesAsPersisted(List<ChatMessage> messages) {
        messages.forEach(message -> message.setPersisted(true));
        // 저장된 메시지는 큐에서 제거
        messageQueue.removeIf(ChatMessage::isPersisted);
    }

    public boolean hasEnoughMessages() {
        return getUnpersistedMessages().size() >= BATCH_SIZE;
    }

    public int getQueueSize() {
        return messageQueue.size();
    }

    public static int getBatchSize() {
        return BATCH_SIZE;
    }

}
