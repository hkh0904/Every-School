package com.everyschool.schoolservice.api.controller.schooluser.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MyClassStudentResponse {

    private Long userId;
    private Integer studentNumber;
    private String name;
    private String birth;

    @Builder
    public MyClassStudentResponse(Long userId, Integer studentNumber, String name, String birth) {
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.name = name;
        this.birth = birth;
    }
}
