package org.example.websockettest.dao;

import jakarta.servlet.http.HttpSession;

public class chatRoomDao {
    public long getChatRoomId(HttpSession httpSession) {
        return 0;
//        httpSession => 로그인 되어있는 session
//        otherName => 내가 채팅 걸 session

    }

/*      채팅방 생성할 때 header에 먼저 생성할거임.. 그래야 session별로 chat_id에 넣고
        따올 수 있음.
        생성 할 때는
        insert into header values (chat_id(long/bigInt), match_id, board_idx, userName1, userName2)


        db 컬럼명 바꾸는거 꼭 말하기!!!!!!!
        dto도 바꾸기~
* */
}
