package com.everyschool.reportservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolUserInfo {

    private Integer grade;
    private Integer classNum;
    private Integer studentNum;

    @Builder
    public SchoolUserInfo(Integer grade, Integer classNum, Integer studentNum) {
        this.grade = grade;
        this.classNum = classNum;
        this.studentNum = studentNum;
    }
}
