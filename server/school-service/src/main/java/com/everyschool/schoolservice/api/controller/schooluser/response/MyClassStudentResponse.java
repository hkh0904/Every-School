package com.everyschool.schoolservice.api.controller.schooluser.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MyClassStudentResponse {

    private Long userId;
    private String studentId;
    private String name;
    private String birth;

    @Builder
    public MyClassStudentResponse(Long userId, String studentId, String name, String birth) {
        this.userId = userId;
        this.studentId = studentId;
        this.name = name;
        this.birth = birth;
    }
}
