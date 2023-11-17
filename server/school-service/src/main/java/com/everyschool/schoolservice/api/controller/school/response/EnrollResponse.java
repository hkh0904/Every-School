package com.everyschool.schoolservice.api.controller.school.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollResponse {

    private int schoolYear;
    private String schoolName;
    private int grade;
    private int classNum;
    private String name;

    private Boolean isApproved;
    private String rejectedReason;

    @Builder
    private EnrollResponse(int schoolYear, String schoolName, int grade, int classNum, String name, Boolean isApproved, String rejectedReason) {
        this.schoolYear = schoolYear;
        this.schoolName = schoolName;
        this.grade = grade;
        this.classNum = classNum;
        this.name = name;
        this.isApproved = isApproved;
        this.rejectedReason = rejectedReason;
    }
}
