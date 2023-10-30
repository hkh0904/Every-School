package com.everyschool.schoolservice.api.service.schoolclass.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSchoolClassDto {

    private String userKey;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolClassDto(String userKey, Integer schoolYear, Integer grade, Integer classNum) {
        this.userKey = userKey;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }
}
