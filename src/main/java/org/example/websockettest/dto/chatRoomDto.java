package org.example.websockettest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class chatRoomDto {
    private long chat_id;
    private long match_id;
    private long board_idx;
    private String email;
    private String subject;
}
