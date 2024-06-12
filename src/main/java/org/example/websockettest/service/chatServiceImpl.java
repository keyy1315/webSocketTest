package org.example.websockettest.service;

import org.example.websockettest.dao.chatRoomDao;
import org.example.websockettest.dto.chatRoomDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatServiceImpl implements chatService{
    public chatRoomDao chatRoomDao;
    @Override
    public void makeChatRoom(chatRoomDto chatRoomDto) {
        chatRoomDao.makeChatRoom(chatRoomDto);
    }
    @Override
    public long getChatIdByUsers(String senderName, String receiverName) {
        Map<String, String> map = new HashMap<>();
        map.put(senderName,receiverName);
        return chatRoomDao.getChatRoomIdByUsers(map);
    }

    @Override
    public List<String> getChatIdByUserValue(String value, String userName) {
        return chatRoomDao.getChatIdByUserValue(value, userName);
    }
}
