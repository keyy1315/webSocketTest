package org.example.websockettest.websocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.example.websockettest.service.chatService;
import org.example.websockettest.service.boardService;

import java.io.IOException;
import java.util.*;
import java.util.Map;


@ServerEndpoint(value = "/web",
        configurator = Config.class)
public class WebSocketServer {
    private static Map<Long, List<Session>> chatRoomSessionMap = Collections.synchronizedMap(new HashMap<>());

    private static Map<Long, Set<chatSession>> chatRoomSessions = Collections.synchronizedMap(new HashMap<>());
    // 채팅방 아이디(key), value(user1 ws Session, user2 ws Session)
//    map(map)
//    value map(key->httpSession. value->ws Session) 2번째 키를 리시버의 httpSession을 넣어둠.
    private static Map<Session, HttpSession> clientsMap = new HashMap<Session, HttpSession>();
    // login id 얻어오기 위해 ws session, http session 담은 맵
    private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
//     websocket session list
//    server에 연결된 모든 client들의 ws Session이 들어가요

    chatService chatService;
    boardService boardService;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // client가 socket에 연결했을 떄...
        System.out.println("client is now connected... : " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");

        System.out.println(session.getRequestURI());

        sessionList.add(session);
        System.out.println("allSessions : "+sessionList);
        clientsMap.put(session, httpSession);

        List<Session> httpSessions = new ArrayList<>();

        long chat_id = 0;
        String[] urls = session.getRequestURI().toString().split("/");
        for (String url : urls) {
            if (url.startsWith("board_id")) {
//                게시판에서 채팅 시작 할 때!!!!!!!!!!
//                보드아이디 따왓으니까 여기서 finduseridbyboardno
                long board_id = Long.parseLong(url.substring(9));
                chat_id = chatService.getChatIdByUsers(httpSession.toString(), boardService.getUserIdByBoardId(board_id));

                chatRoomSessionMap.put(chat_id, httpSessions);
            }
            else {
//                receiver가 채팅 오픈 했을 때!!


            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session wsSession) {
        // 메세지 핸들러...
        // client 가 전송한 메세지 핸들링함
        System.out.println("ws Session( " + wsSession.getId() + " ) : " + message);
        HttpSession httpSession = clientsMap.get(wsSession);
//        websocket 접속한 wsSession의 httpSession값
        String[] strings = message.split(":");
        long chat_id = Long.parseLong(strings[0]);
//        client가 전송한 메세지에 담겨있는 chat_id
        String msg = strings[1];

        chatRoomSessionMap.get(chat_id).forEach(session -> {
//        sessionList.forEach(session -> {
            try {
                if (session == wsSession) {
                    session.getBasicRemote().sendText("나|" + msg);
                } else {
                    String sessionId = httpSession.getId();
                    session.getBasicRemote().sendText(sessionId + "|" + msg);
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
