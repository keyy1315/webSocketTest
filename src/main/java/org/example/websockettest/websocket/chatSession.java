package org.example.websockettest.websocket;

import jakarta.websocket.Session;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class chatSession {
    private Session session;
    private long chatRoomId;
    public void sendMessage(String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
