package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthEmailRequest {

    private String email;

    @Builder
    private AuthEmailRequest(String email) {
        this.email = email;
    }
}
