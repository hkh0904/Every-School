package com.everyschool.schoolservice.api.service.schoolclass.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSchoolClassDto {

    private Long schoolId;
    private String userKey;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolClassDto(Long schoolId, String userKey, Integer schoolYear, Integer grade, Integer classNum) {
        this.schoolId = schoolId;
        this.userKey = userKey;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }
}
