package org.example.websockettest.websocket;

import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class WebSocketClient {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server: " + session.getId());
        try {
            session.getBasicRemote().sendText("Hello Server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected from server: " + session.getId());
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from server: " + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred: " + throwable.getMessage());
    }

}
