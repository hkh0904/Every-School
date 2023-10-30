package com.everyschool.schoolservice.api.controller.school.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollRequest {
    private char type;

    private String schoolName;
    private String name;
    private int grade;
    private int classNum;
    private int schoolYear;

    @Builder
    private EnrollRequest(String schoolName, String name, int grade, int classNum, int schoolYear, char type) {
        this.schoolName = schoolName;
        this.name = name;
        this.grade = grade;
        this.classNum = classNum;
        this.schoolYear = schoolYear;
        this.type = type;
    }
}
