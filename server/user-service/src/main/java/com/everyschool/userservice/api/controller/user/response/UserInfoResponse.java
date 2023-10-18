package com.everyschool.userservice.api.controller.user.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

    private String type;
    private String email;
    private String name;
    private String birth;
    private LocalDateTime joinDate;

    @Builder
    public UserInfoResponse(String type, String email, String name, String birth, LocalDateTime joinDate) {
        this.type = type;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.joinDate = joinDate;
    }
}
