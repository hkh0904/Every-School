package com.everyschool.userservice.api.client.school.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DescendantInfo {

    private Long userId;
    private String schoolName;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;
    private Integer studentNumber;

    @Builder
    public DescendantInfo(Long userId, String schoolName, Integer schoolYear, Integer grade, Integer classNum, Integer studentNumber) {
        this.userId = userId;
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
        this.studentNumber = studentNumber;
    }
}
