package org.example.websockettest.service;

import org.example.websockettest.dto.chatRoomDto;

import java.util.List;

public interface chatService {
    void makeChatRoom(chatRoomDto chatRoomDto);
    long getChatIdByUsers(String senderName, String receiverName);

    List<String> getChatIdByUserValue(String value, String userName);
}
