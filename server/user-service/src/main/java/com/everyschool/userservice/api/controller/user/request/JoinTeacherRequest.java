package com.everyschool.userservice.api.controller.user.request;

import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class JoinTeacherRequest {


    @NotNull
    private Integer userCode;

    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;

    @NotEmpty
    @Size(max = 20)
    private String password;

    @NotEmpty
    @Size(max = 20)
    private String name;

    @NotEmpty
    @Size(min = 10, max = 10)
    private String birth;

    @Builder
    private JoinTeacherRequest(Integer userCode, String email, String password, String name, String birth) {
        this.userCode = userCode;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }

    public CreateUserDto toDto() {
        return CreateUserDto.builder()
            .userCodeId(this.userCode)
            .email(this.email)
            .pwd(this.password)
            .name(this.name)
            .birth(this.birth)
            .build();
    }
}
