package com.everyschool.schoolservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class EditStudentClassInfoDto {

    private Long studentId;
    private Long schoolId;
    private Long schoolClassId;

    @Builder
    public EditStudentClassInfoDto(Long studentId, Long schoolId, Long schoolClassId) {
        this.studentId = studentId;
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
    }
}
