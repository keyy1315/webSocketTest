package org.example.websockettest.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint("/websocket")
public class WebSocketServer {

    // WebSocket의 호스트 주소 설정
    @OnOpen
    public void onOpen(Session session) {
        // 콘솔에 접속 로그를 출력한다.
        System.out.println("client is now connected... : " + session.getId());
    }

    // WebSocket으로 메시지가 오면 요청되는 함수
    @OnMessage
    public void onMessage(String message, Session session) {
        // 메시지 내용을 콘솔에 출력한다.
        System.out.println("receive from client : " + message);
        // 에코 메시지를 작성한다.
        try {
            session.getBasicRemote().sendText("Echo: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
    @OnClose
    public void onClose(Session session) {
        // 콘솔에 접속 끊김 로그를 출력한다.
        System.out.println("client is now disconnected... : "+session.getId());
    }

    // WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
    @OnError
    public void onError(Throwable t,Session session) {
        // 콘솔에 에러를 표시한다.
        System.out.println("Error!! : "+t.getMessage());
    }
}
