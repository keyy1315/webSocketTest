package com.ohboon.ohboon.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String email;
    private String nickName;
    private String userName;
    private String birth;
    private String phone;
    private boolean available;
    private String userPW;
    private String  grade;
    private String meetingLocation;
    private String meetingTime;
    private int evaluation;
    private String profile;
}
