package org.example.websockettest.dao;

import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.example.websockettest.dto.chatRoomDto;
import org.example.websockettest.util.MybatisConnectionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatRoomDao {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

    public long getChatRoomIdByUsers(Map chatUsers) {
        long chatRoomId = ss.selectOne("getChatRoomIdByUsers", chatUsers);
        ss.close();
        return chatRoomId;
    }

    public int makeChatRoom(chatRoomDto chatRoomDto) {
        int rs = ss.insert("setChatRoom", chatRoomDto);
        ss.close();
        return rs;
    }

    public List<String> getChatIdByUserValue(String value, String userName) {
        Map<String, String> map = new HashMap<>();
        map.put(value,userName);
        List<String> chatRoomId = ss.selectList("getChatIdByUserValue", map);
        ss.close();
        return chatRoomId;
    }

/*      채팅방 생성할 때 header에 먼저 생성할거임.. 그래야 session별로 chat_id에 넣고
        따올 수 있음.
        생성 할 때는
        insert into header values (chat_id(long/bigInt), match_id, board_idx, userName1, userName2)
        select chat_id from header where sender=? || receiver =?
        user1 user2
        user1 user3

        채팅버튼 -> header col (sender, receiver)-> ws open(sender) (chatroomid server handling 역할) ->

        sender(채팅 건 사람) // receiver(채팅 받은 사람. 게시글 작성자) -> match에서
        sender(채팅 건 사람) // receiver(채팅 받은 사람)
        dto도 바꾸기~
* */
}
