package org.example.websockettest.websocket;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/websocket", configurator = Config.class)
public class ServerEndPoint {
    private static Map<Session, HttpSession> sessionsMap = new HashMap<>();
    private static List<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");
        sessionsMap.put(session, httpSession);
        sessionList.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("ws Session( " + session.getId() + " ) : " + message);
        HttpSession httpSession = sessionsMap.get(session);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        long chat_id = jsonObject.get("chat_id").getAsLong();
        String chat_ids = jsonObject.get("chat_id").getAsString();

        String sender = jsonObject.get("sender").getAsString();
        String content = jsonObject.get("content").getAsString();
        String timestamp = jsonObject.get("timestamp").getAsString();

        System.out.println(chat_id);
        System.out.println(chat_ids);
        System.out.println(sender);
        System.out.println(content);
        System.out.println(timestamp);

        for (Session session1 : sessionList) {
            try {
                session1.getBasicRemote().sendText(sender + ":" + content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        // 콘솔에 접속 끊김 로그를 출력한다.
        System.out.println("client is now disconnected... : " + session.getId());
    }

    @OnError
    public void onError(Throwable t, Session session) {
        // 콘솔에 에러를 표시한다.
        System.out.println("Error!! : " + t.getMessage());
    }
}
