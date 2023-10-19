package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthEmailCheckRequest {

    private String email;
    private String authCode;

    @Builder
    private AuthEmailCheckRequest(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }
}
