package com.everyschool.schoolservice.api.controller.school.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassroomRequest {
    private Long teacherId;
    private int year;
    private int grade;
    private String name;

    @Builder
    public ClassroomRequest(Long teacherId, int year, int grade, String name) {
        this.teacherId = teacherId;
        this.year = year;
        this.grade = grade;
        this.name = name;
    }
}
