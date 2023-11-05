package com.everyschool.schoolservice.api.service.schooluser.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MyClassStudentDto {

    private Long studentId;
    private Integer studentNumber;

    @Builder
    public MyClassStudentDto(Long studentId, Integer studentNumber) {
        this.studentId = studentId;
        this.studentNumber = studentNumber;
    }
}
