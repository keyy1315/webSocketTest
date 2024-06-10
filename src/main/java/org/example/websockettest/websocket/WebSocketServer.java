package org.example.websockettest.websocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/websocket")
public class WebSocketServer {

//    private static Map<Session, HttpSession> clientsMap = new HashMap<Session, HttpSession>();
//      websocket session이 키 값...
    static List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    // WebSocket의 호스트 주소 설정
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 콘솔에 접속 로그를 출력한다.
        System.out.println("client is now connected... : " + session.getId());
//        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");

        sessionList.add(session);
        System.out.println(sessionList);
    }

    // WebSocket으로 메시지가 오면 요청되는 함수
    @OnMessage
    public void onMessage(String message, Session session) {
        // 메시지 내용을 콘솔에 출력한다.
        System.out.println("receive from client : " + session.getId() + " : " + message);
//        HttpSession httpSession = clientsMap.get(session);
        try {
            synchronized (sessionList) {

                for (Session client :sessionList) {
//                    clients 에 들어있는 모든 유저에게 메세지 전달 할 거임(보낸 사람도 포함)
//                    if (!session.equals(client)) {
                        session.getBasicRemote().sendText(message);
                        System.out.println("basic remote : "+session.getBasicRemote());

//                    }
                }
            }
            // remoteEndpoint 리턴
        } catch (IOException e) {
            e.printStackTrace();
        }

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
