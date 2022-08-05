package com.cogether.api.chat.service;

import com.cogether.api.chat.domain.*;
import com.cogether.api.chat.exception.ChatNotFoundException;
import com.cogether.api.chat.exception.ChatRoomNotFoundException;
import com.cogether.api.chat.repository.ChatMemRepository;
import com.cogether.api.chat.repository.ChatRepository;
import com.cogether.api.chat.repository.ChatRoomRepository;
import com.cogether.api.liveCoop.domain.LiveCoop;
import com.cogether.api.liveCoop.domain.LiveCoopRequest;
import com.cogether.api.liveCoop.domain.LiveCoopResponse;
import com.cogether.api.liveCoop.exception.LiveCoopNotFoundException;
import com.cogether.api.liveCoop.repository.LiveCoopRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemRepository chatMemRepository;
    private final UserRepository userRepository;

    public ChatResponse.OnlyChatId createChat(ChatRequest.CreateChat request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        // User user = userRepository.findById(request.getSendUserId()).orElseThrow(UserNotFoundException::new);
        Chat chat = request.toEntity(chatRoom);
        Chat savedChat = chatRepository.save(chat);
        return ChatResponse.OnlyChatId.build(savedChat);
    }

    public ChatResponse.GetChat getChat(int chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
        User user = userRepository.findById(chat.getSendUserId()).orElseThrow(UserNotFoundException::new);

        return ChatResponse.GetChat.build(chat, user);
    }

    public ChatResponse.GetChats getChats(int chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        List<Chat> chats = chatRepository.findAllByChatRoom(chatRoom);
        List<ChatResponse.GetChat> getChats = new ArrayList<>();
        int len = chats.size();

        for (int idx = 0; idx < len; idx++) {
            Chat chat = chats.get(idx);
            User user = userRepository.findById(chat.getSendUserId()).orElseThrow(UserNotFoundException::new);
            getChats.add(ChatResponse.GetChat.build(chat, user));
        }

        return ChatResponse.GetChats.build(getChats);
    }

    public ChatResponse.OnlyMemId createChatMember(ChatRequest.CreateChatMember request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        ChatMember chatMember = request.toEntity(chatRoom, user);
        ChatMember savedChatMember = chatMemRepository.save(chatMember);
        return ChatResponse.OnlyMemId.build(savedChatMember);
    }

    public ChatResponse.OnlyMemId deleteChatMember(int roomId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        ChatMember chatMember = chatMemRepository.findByChatRoomAndUser(chatRoom, user);
        chatMemRepository.deleteChatMemberByChatRoomAndUser(chatRoom, user);

        return ChatResponse.OnlyMemId.build(chatMember);
    }

    public ChatResponse.OnlyRoomId createChatRoom(ChatRequest.CreateChatRoom request) {
        ChatRoom chatRoom = request.toEntity();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return ChatResponse.OnlyRoomId.build(savedChatRoom);
    }

    public ChatResponse.GetChatRoom getChatRoom(int chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        List<ChatMember> chatMembers = chatMemRepository.findAllByChatRoom(chatRoom);
        return ChatResponse.GetChatRoom.build(chatRoom, chatMembers);
    }

    public ChatResponse.GetChatRooms getChatRooms(int userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<ChatMember> chatMems = chatMemRepository.findAllByUser(user);
        List<ChatResponse.GetChatRoom> getChatRooms = new ArrayList<>();
        int len = chatMems.size();

        for (int idx = 0; idx < len; idx++) {
            ChatRoom chatRoom = chatMems.get(idx).getChatRoom();
            List<ChatMember> chatMembers = chatMemRepository.findAllByChatRoom(chatRoom);
            getChatRooms.add(ChatResponse.GetChatRoom.build(chatRoom, chatMembers));
        }

        return ChatResponse.GetChatRooms.build(getChatRooms);
    }

    public ChatResponse.OnlyRoomId deleteChatRoom(int chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(ChatRoomNotFoundException::new);
        chatRoomRepository.deleteById(chatRoomId);
        return ChatResponse.OnlyRoomId.build(chatRoom);
    }

}