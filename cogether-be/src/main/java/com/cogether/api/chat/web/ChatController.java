package com.cogether.api.chat.web;

import com.cogether.api.chat.domain.ChatRequest;
import com.cogether.api.chat.domain.ChatResponse;
import com.cogether.api.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ChatResponse.OnlyChatId> createChat(@RequestBody ChatRequest.CreateChat request) {
        ChatResponse.OnlyChatId response = chatService.createChat(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<ChatResponse.GetChat> getLiveCoop(@PathVariable int chatId) {
        ChatResponse.GetChat response = chatService.getChat(chatId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chat/list/{chatRoomId}")
    public ResponseEntity<ChatResponse.GetChats> getChats(@PathVariable int chatRoomId) {
        ChatResponse.GetChats response = chatService.getChats(chatRoomId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/chat/member")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ChatResponse.OnlyMemId> createChatMember(@RequestBody ChatRequest.CreateChatMember request) {
        ChatResponse.OnlyMemId response = chatService.createChatMember(request);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/chat/member")
    public ResponseEntity<ChatResponse.OnlyMemId> updateLastChat(@RequestBody ChatRequest.UpdateLastChat request) {
        ChatResponse.OnlyMemId response = chatService.updateLastChat(request);
        return ResponseEntity.ok().body(response);
    }


//    @Transactional
//    @DeleteMapping("/chat/member")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<ChatResponse.OnlyMemId> deleteChatMember(@RequestParam int chatRoomId, @RequestParam int userId) {
//        ChatResponse.OnlyMemId response = chatService.deleteChatMember(chatRoomId, userId);
//        return ResponseEntity.ok().body(response);
//    }

    @PostMapping("/chat/room")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ChatResponse.OnlyRoomId> createChatRoom(@RequestBody ChatRequest.CreateChatRoom request) {
        ChatResponse.OnlyRoomId response = chatService.createChatRoom(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chat/room/{chatRoomId}")
    public ResponseEntity<ChatResponse.GetChatRoom> getChatRoom(@PathVariable int chatRoomId) {
        ChatResponse.GetChatRoom response = chatService.getChatRoom(chatRoomId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chat/room/list/{userId}")
    public ResponseEntity<ChatResponse.GetChatRooms> getChatRooms(@PathVariable int userId) {
        ChatResponse.GetChatRooms response = chatService.getChatRooms(userId);
        return ResponseEntity.ok().body(response);
    }

//    @Transactional
//    @DeleteMapping("/chat/room/{chatRoomId}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<ChatResponse.OnlyRoomId> deleteChatRoom(@PathVariable int chatRoomId) {
//        ChatResponse.OnlyRoomId response = chatService.deleteChatRoom(chatRoomId);
//        return ResponseEntity.ok().body(response);
//    }

}




//
//import com.cogether.api.chat.dto.ChatMessage;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class ChatController {
//
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        return chatMessage;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }
//}

