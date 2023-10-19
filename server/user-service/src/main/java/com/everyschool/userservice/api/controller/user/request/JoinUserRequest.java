package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinUserRequest {

    private Integer userCode;
    private String email;
    private String password;
    private String name;
    private String birth;

    @Builder
    public JoinUserRequest(Integer userCode, String email, String password, String name, String birth) {
        this.userCode = userCode;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }
}
