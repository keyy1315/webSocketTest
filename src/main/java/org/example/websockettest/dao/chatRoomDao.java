package org.example.websockettest.dao;

import jakarta.servlet.http.HttpSession;

public class chatRoomDao {
    public long getChatRoomId(HttpSession httpSession) {
        return 0;
//        sql : select chat_id from header where subject = 'httpSession' || email = 'httpSession';
    }

/*      채팅방 생성할 때 header에 먼저 생성할거임.. 그래야 session별로 chat_id에 넣고
        따올 수 있음.
        생성 할 때는
        insert into header values (chat_id(long/bigInt), match_id, board_idx, 채팅 보낼 사람, 채팅방 생성한 사람)
* */
}
