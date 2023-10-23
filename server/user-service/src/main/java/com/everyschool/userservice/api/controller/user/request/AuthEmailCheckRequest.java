package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthEmailCheckRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String authCode;

    @Builder
    private AuthEmailCheckRequest(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }
}
