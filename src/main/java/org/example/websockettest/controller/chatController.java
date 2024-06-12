package org.example.websockettest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.websockettest.dto.chatRoomDto;
import org.example.websockettest.service.chatService;
import org.example.websockettest.websocket.chatSession;
import org.example.websockettest.service.boardService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/startChat")
public class chatController extends HttpServlet {
    private chatService chatService;
    private boardService boardService;
    private chatSession chatSession;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String senderName = (String) httpSession.getAttribute("sessionUserId");
        long boardId = Long.parseLong(req.getParameter("no"));
        String receiverName = boardService.getUserIdByBoardId(boardId);
        chatRoomDto chatRoomDto = org.example.websockettest.dto.chatRoomDto.builder()
                .subject(receiverName)
                .email(senderName)
                .build();
        chatService.makeChatRoom(chatRoomDto);
        List<chatRoomDto> chatRoomList = new ArrayList<>();
        chatRoomList.add(chatRoomDto);

        req.setAttribute("chatRoomDto", chatRoomDto);
        req.setAttribute("chatRoomList", chatRoomList);
    }
}
