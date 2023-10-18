package com.everyschool.userservice.api.controller.user.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String email;
    private String name;
    private String type;
    private LocalDateTime createdDate;

    @Builder
    public UserResponse(String email, String name, String type, LocalDateTime createdDate) {
        this.email = email;
        this.name = name;
        this.type = type;
        this.createdDate = createdDate;
    }
}
