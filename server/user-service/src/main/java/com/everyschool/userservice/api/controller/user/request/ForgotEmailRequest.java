package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotEmailRequest {

    private String name;
    private String birth;

    @Builder
    private ForgotEmailRequest(String name, String birth) {
        this.name = name;
        this.birth = birth;
    }
}
