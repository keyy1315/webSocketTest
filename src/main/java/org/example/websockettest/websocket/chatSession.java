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
//    wsSession 객체 래핑!... chatroom관리할거임
    private Session senderSession;
    private Session receiverSession;
    private String senderId;
    private String receiverId;
    private long chat_id;
}
