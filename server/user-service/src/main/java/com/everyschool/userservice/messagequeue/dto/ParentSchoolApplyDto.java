package com.everyschool.userservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ParentSchoolApplyDto {

    private Long parentId;
    private Long studentId;
    private Long schoolClassId;

    @Builder
    public ParentSchoolApplyDto(Long parentId, Long studentId, Long schoolClassId) {
        this.parentId = parentId;
        this.studentId = studentId;
        this.schoolClassId = schoolClassId;
    }
}
