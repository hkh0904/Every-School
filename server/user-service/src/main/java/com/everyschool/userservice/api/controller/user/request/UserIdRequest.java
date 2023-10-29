package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdRequest {

    private String userKey;

    @Builder
    private UserIdRequest(String userKey) {
        this.userKey = userKey;
    }
}
