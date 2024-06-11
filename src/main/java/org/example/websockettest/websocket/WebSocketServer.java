package org.example.websockettest.websocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;


import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/websocket",
        configurator = Config.class)
public class WebSocketServer {
   // private final Map<Long, List<Session>> chatRoomSessionMap = new HashMap<>();
    private static Map<Session, HttpSession> clientsMap = new HashMap<Session, HttpSession>();
    // login id 얻어오기 위해 ws session, http session 담은 맵
    private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    // websocket session list

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 콘솔에 접속 로그를 출력한다.
        System.out.println("client is now connected... : " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");

        
        sessionList.add(session);
        System.out.println(sessionList);

        clientsMap.put(session, httpSession);
    }

    // WebSocket으로 메시지가 오면 요청되는 함수
    @OnMessage
    public void onMessage(String message, Session session) {
        // 메시지 내용을 콘솔에 출력한다.
        System.out.println("receive from client : " + session.getId() + " : " + message);
        HttpSession httpSession = clientsMap.get(session);
        sessionList.forEach(session1 -> {
            try {
                if (session1 == session) {
                    session1.getBasicRemote().sendText("나|" + message);
                } else {
                    String sessionId = httpSession.getId();
                    session1.getBasicRemote().sendText(sessionId + "|" + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
    @OnClose
    public void onClose(Session session) {
        // 콘솔에 접속 끊김 로그를 출력한다.
        System.out.println("client is now disconnected... : " + session.getId());
    }

    // WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
    @OnError
    public void onError(Throwable t, Session session) {
        // 콘솔에 에러를 표시한다.
        System.out.println("Error!! : " + t.getMessage());
    }
}
