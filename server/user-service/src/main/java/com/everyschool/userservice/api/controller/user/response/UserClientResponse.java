package com.everyschool.userservice.api.controller.user.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserClientResponse {

    private Long userId;

    @Builder
    public UserClientResponse(Long userId) {
        this.userId = userId;
    }
}
