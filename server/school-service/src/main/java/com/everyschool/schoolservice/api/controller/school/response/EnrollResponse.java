package com.everyschool.schoolservice.api.controller.school.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollResponse {

    private int year;
    private String schoolName;
    private int grade;
    private int classNum;
    private String name;

    private Boolean isApproved;
    private String rejectedReason;

    @Builder

    public EnrollResponse(int year, String schoolName, int grade, int classNum, String name, Boolean isApproved, String rejectedReason) {
        this.year = year;
        this.schoolName = schoolName;
        this.grade = grade;
        this.classNum = classNum;
        this.name = name;
        this.isApproved = isApproved;
        this.rejectedReason = rejectedReason;
    }
}
