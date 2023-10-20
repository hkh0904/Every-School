package com.everyschool.schoolservice.api.controller.enroll.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollRequest {
    private String schoolName;
    private String name;
    private int grade;
    private int classNum;
    private int year;
    private char type;


    @Builder
    public EnrollRequest(String schoolName, String name, int grade, int classNum, int year, char type) {
        this.schoolName = schoolName;
        this.name = name;
        this.grade = grade;
        this.classNum = classNum;
        this.year = year;
        this.type = type;
    }
}
