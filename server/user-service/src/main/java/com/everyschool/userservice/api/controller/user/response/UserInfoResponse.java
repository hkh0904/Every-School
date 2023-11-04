package com.everyschool.userservice.api.controller.user.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.everyschool.userservice.domain.user.UserType.getText;

@Data
public class UserInfoResponse {

    private String type;
    private String email;
    private String name;
    private String birth;
    private LocalDateTime joinDate;

    @Builder
    public UserInfoResponse(int userTypeId, String email, String name, String birth, LocalDateTime joinDate) {
        this.type = getText(userTypeId);
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.joinDate = joinDate;
    }
}
