package com.everyschool.userservice.api.client.school.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolClassInfo {

    private String schoolName;
    private Integer grade;
    private Integer classNum;

    @Builder
    public SchoolClassInfo(String schoolName, Integer grade, Integer classNum) {
        this.schoolName = schoolName;
        this.grade = grade;
        this.classNum = classNum;
    }
}
