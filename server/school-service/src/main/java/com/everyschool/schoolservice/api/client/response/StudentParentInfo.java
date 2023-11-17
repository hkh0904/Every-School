package com.everyschool.schoolservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentParentInfo {

    private Long studentId;
    private Long parentId;
    private char parentType;

    @Builder
    public StudentParentInfo(Long studentId, Long parentId, char parentType) {
        this.studentId = studentId;
        this.parentId = parentId;
        this.parentType = parentType;
    }
}
