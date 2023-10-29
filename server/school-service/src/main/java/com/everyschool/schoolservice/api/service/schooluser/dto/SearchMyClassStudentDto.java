package com.everyschool.schoolservice.api.service.schooluser.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchMyClassStudentDto {

    private Long studentId;
    private Integer studentNum;

    @Builder
    public SearchMyClassStudentDto(Long studentId, Integer studentNum) {
        this.studentId = studentId;
        this.studentNum = studentNum;
    }
}
