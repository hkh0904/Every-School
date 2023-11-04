package com.everyschool.userservice.api.controller.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentParentInfo {

    private Long studentId;
    private Long parentId;
    private char parentType;

    @Builder
    public StudentParentInfo(Long studentId, Long parentId, String parentType) {
        this.studentId = studentId;
        this.parentId = parentId;
        this.parentType = parentType.charAt(0);
    }
}
