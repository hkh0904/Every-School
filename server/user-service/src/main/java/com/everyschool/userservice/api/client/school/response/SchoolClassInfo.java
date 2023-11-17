package com.everyschool.userservice.api.client.school.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolClassInfo {

    private String schoolName;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    public SchoolClassInfo(String schoolName, Integer schoolYear, Integer grade, Integer classNum) {
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }
}
