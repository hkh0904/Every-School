package com.everyschool.schoolservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateStudentParentDto {

    private Long studentId;
    private Long parentId;

    @Builder
    public CreateStudentParentDto(Long studentId, Long parentId) {
        this.studentId = studentId;
        this.parentId = parentId;
    }
}
