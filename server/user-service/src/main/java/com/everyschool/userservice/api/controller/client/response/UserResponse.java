package com.everyschool.userservice.api.controller.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String name;
    private String birth;

    @Builder
    public UserResponse(Long userId, String name, String birth) {
        this.userId = userId;
        this.name = name;
        this.birth = birth;
    }
}
