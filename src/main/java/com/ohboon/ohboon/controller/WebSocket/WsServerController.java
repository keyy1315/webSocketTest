package com.ohboon.ohboon.controller.WebSocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import util.WsHttpSessionConfig;

@ServerEndpoint(value = "/chat", configurator = WsHttpSessionConfig.class)
public class WsServerController {
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //client가 websocket에 연결되었을 때

    }
    @OnMessage
    public void onMessage(String message, Session wsSession) {
//        client가 server에 msg 보냈을 때 핸들링
    }
    @OnClose
    public void onClose(Session session) {
//        클라이언트가 ws서버 연결이 끊겼을 때 wsSession 출력
        System.out.println("client is now disconnected... : " + session.getId());
    }
    @OnError
    public void onError(Throwable t, Session session) {
//        error
        System.out.println("Error!! : " + t.getMessage());
    }
}
