package com.everyschool.userservice.api.app.controller.user.response.info;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolClass {

    private Long schoolClassId;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private SchoolClass(Long schoolClassId, Integer schoolYear, Integer grade, Integer classNum) {
        this.schoolClassId = schoolClassId;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }
}
