package com.everyschool.reportservice.api.controller.report.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private int grade;
    private int classNum;
    private String name;

    @Builder
    public UserResponse(int grade, int classNum, String name) {
        this.grade = grade;
        this.classNum = classNum;
        this.name = name;
    }
}
