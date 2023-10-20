package com.everyschool.schoolservice.api.controller.school.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEnrollResponse {
    private char type;

    private String childName;
    private int grade;
    private int classNum;
    private int studentNum;

    private LocalDateTime appliedDate;

    @Builder
    public UserEnrollResponse(char type, String childName, int grade, int classNum, int studentNum, LocalDateTime appliedDate) {
        this.type = type;
        this.childName = childName;
        this.grade = grade;
        this.classNum = classNum;
        this.studentNum = studentNum;
        this.appliedDate = appliedDate;
    }
}
