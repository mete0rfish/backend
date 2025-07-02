package com.onetool.server.api.chat.controller;

import com.onetool.server.api.chat.domain.ChatRoom;
import com.onetool.server.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @RequestMapping("/chat/chatList")
    public List<ChatRoom> chatList(){
        return chatService.findAllRoom();
    }

    @PostMapping("/chat/createRoom")
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/chat/chatRoom")
    public ChatRoom chatRoom(@RequestParam String roomId) {
        return chatService.findRoomById(roomId);
    }
}
