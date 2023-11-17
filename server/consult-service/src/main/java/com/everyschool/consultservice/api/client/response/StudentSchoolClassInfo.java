package com.everyschool.consultservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentSchoolClassInfo {

    private Integer grade;
    private Integer classNum;
    private Integer studentNum;

    @Builder
    public StudentSchoolClassInfo(Integer grade, Integer classNum, Integer studentNum) {
        this.grade = grade;
        this.classNum = classNum;
        this.studentNum = studentNum;
    }
}
