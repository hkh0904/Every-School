package com.everyschool.userservice.api.controller.user.response;

import com.everyschool.userservice.domain.user.User;
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

    // TODO: 10/22/23 type enum으로 분리하는 방법 생각
    public static UserResponse of(User user) {
        return UserResponse.builder()
            .email(user.getEmail())
            .name(user.getName())
            .type("none")
            .createdDate(user.getCreatedDate())
            .build();
    }
}
