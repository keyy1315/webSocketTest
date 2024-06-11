package org.example.websockettest.websocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.example.websockettest.dao.chatRoomDao;


import java.io.IOException;
import java.net.URI;
import java.util.*;

@ServerEndpoint(value = "/websocket",
        configurator = Config.class)
public class WebSocketServer {
    private static Map<Long, List<Session>> chatRoomSessionMap = Collections.synchronizedMap(new HashMap<>());
    private static Map<Session, HttpSession> clientsMap = new HashMap<Session, HttpSession>();
    // login id 얻어오기 위해 ws session, http session 담은 맵
    private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    // websocket session list

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // client가 socket에 연결했을 떄...
        System.out.println("client is now connected... : " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");

        System.out.println(session.getRequestURI());
        System.out.println(session.getPathParameters());

        sessionList.add(session);
        System.out.println(sessionList);
        clientsMap.put(session, httpSession);

        long chatRoomID = (long) (Math.random() * 100);
        chatRoomSessionMap.put(chatRoomID, sessionList);
    }

    private long setChatRoomId(HttpSession httpSession) {
        chatRoomDao chatRoomDao = new chatRoomDao();
        return chatRoomDao.getChatRoomId(httpSession);
    }

    @OnMessage
    public void onMessage(String message, Session wsSession) {
        // 메세지 핸들러...
        // client 가 전송한 메세지 핸들링함
        System.out.println("ws Session( "+wsSession.getId() + " ) : " + message);
        HttpSession httpSession = clientsMap.get(wsSession);
//        websocket 접속한 wsSession의 httpSession값

        sessionList.forEach(session -> {
            try {
                if (session == wsSession) {
                    session.getBasicRemote().sendText("나|" + message);
                } else {
                    String sessionId = httpSession.getId();
                    session.getBasicRemote().sendText(sessionId + "|" + message);
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
