package com.everyschool.schoolservice.api.controller.school.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassroomResponse {

    private String teacherName;
    private int schoolYear;
    private int grade;
    private String name;

    @Builder
    private ClassroomResponse(String teacherName, int schoolYear, int grade, String name) {
        this.teacherName = teacherName;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.name = name;
    }
}
