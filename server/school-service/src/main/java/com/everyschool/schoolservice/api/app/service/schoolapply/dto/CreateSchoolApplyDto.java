package com.everyschool.schoolservice.api.app.service.schoolapply.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSchoolApplyDto {

    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolApplyDto(Integer schoolYear, Integer grade, Integer classNum) {
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }
}
