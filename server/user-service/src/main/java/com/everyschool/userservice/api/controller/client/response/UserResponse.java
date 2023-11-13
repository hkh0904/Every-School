package com.everyschool.userservice.api.controller.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

    private Long studentId;
    private String name;
    private String birth;

    @Builder
    public UserResponse(Long studentId, String name, String birth) {
        this.studentId = studentId;
        this.name = name;
        this.birth = birth;
    }
}
