package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPwdRequest {

    private String email;
    private String name;
    private String birth;

    @Builder
    private ForgotPwdRequest(String email, String name, String birth) {
        this.email = email;
        this.name = name;
        this.birth = birth;
    }
}
