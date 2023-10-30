package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ForgotPwdRequest {

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "생년월일은 필수입니다.")
    private String birth;

    @Builder
    private ForgotPwdRequest(String email, String name, String birth) {
        this.email = email;
        this.name = name;
        this.birth = birth;
    }
}
