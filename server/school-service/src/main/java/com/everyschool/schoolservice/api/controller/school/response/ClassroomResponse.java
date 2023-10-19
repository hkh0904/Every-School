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
    private int year;
    private int grade;
    private String name;

    @Builder
    public ClassroomResponse(String teacherName, int year, int grade, String name) {
        this.teacherName = teacherName;
        this.year = year;
        this.grade = grade;
        this.name = name;
    }
}
