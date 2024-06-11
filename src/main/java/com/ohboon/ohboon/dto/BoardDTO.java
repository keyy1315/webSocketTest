package com.ohboon.ohboon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardDTO {
    private long id;
    private String email;
    private String subject;
    private String content;
    private LocalDateTime regDate;
    private int viewOption;
    private String category;
    private String location;
    private LocalDateTime meetDate;
    private LocalDateTime modifyDate;
}
