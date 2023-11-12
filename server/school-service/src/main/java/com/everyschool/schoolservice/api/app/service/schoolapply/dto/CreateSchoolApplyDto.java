package com.everyschool.schoolservice.api.app.service.schoolapply.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSchoolApplyDto {

    private int grade;
    private int classNum;

    @Builder
    private CreateSchoolApplyDto(int grade, int classNum) {
        this.grade = grade;
        this.classNum = classNum;
    }
}
