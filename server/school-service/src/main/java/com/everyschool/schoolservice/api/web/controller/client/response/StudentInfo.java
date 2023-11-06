package com.everyschool.schoolservice.api.web.controller.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentInfo {

    private Integer grade;
    private Integer classNum;
    private Integer studentNum;

    @Builder
    public StudentInfo(Integer grade, Integer classNum, Integer studentNum) {
        this.grade = grade;
        this.classNum = classNum;
        this.studentNum = studentNum;
    }
}
