package com.ohboon.ohboon.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDTO {
    private int id;
    private int boardId;
    private boolean isMath;
}
