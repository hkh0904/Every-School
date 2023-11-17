package com.everyschool.schoolservice.api.controller.school.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassroomRequest {
    private Long teacherId;
    private int schoolYear;
    private int grade;
    private String name;

    @Builder
    private ClassroomRequest(Long teacherId, int schoolYear, int grade, String name) {
        this.teacherId = teacherId;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.name = name;
    }
}
