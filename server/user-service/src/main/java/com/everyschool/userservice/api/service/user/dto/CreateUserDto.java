package com.everyschool.userservice.api.service.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateUserDto {

    private int userCodeId;
    private String email;
    private String pwd;
    private String name;
    private String birth;

    @Builder
    private CreateUserDto(int userCodeId, String email, String pwd, String name, String birth) {
        this.userCodeId = userCodeId;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
    }
}
