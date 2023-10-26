package com.everyschool.userservice.api.controller.user.response;

import com.everyschool.userservice.domain.user.StudentParent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateStudentParentResponse {

    private String parentType;
    private String parentName;
    private String studentName;
    private LocalDateTime createdDate;

    @Builder
    public CreateStudentParentResponse(String parentType, String parentName, String studentName, LocalDateTime createdDate) {
        this.parentType = parentType;
        this.parentName = parentName;
        this.studentName = studentName;
        this.createdDate = createdDate;
    }

    public static CreateStudentParentResponse of(StudentParent studentParent) {
        String parentType = studentParent.getParent().getParentType().equals("M") ? "부" : "모";

        return CreateStudentParentResponse.builder()
            .parentType(parentType)
            .parentName(studentParent.getParent().getName())
            .studentName(studentParent.getStudent().getName())
            .createdDate(studentParent.getCreatedDate())
            .build();
    }
}
