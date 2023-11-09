package com.everyschool.schoolservice.api.app.service.schoolapply.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSchoolApplyDto {

    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolApplyDto(Integer grade, Integer classNum) {
        this.grade = grade;
        this.classNum = classNum;
    }
}
