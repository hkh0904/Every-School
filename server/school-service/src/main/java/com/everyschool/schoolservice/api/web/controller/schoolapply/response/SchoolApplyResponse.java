package com.everyschool.schoolservice.api.web.controller.schoolapply.response;

import com.everyschool.schoolservice.api.client.response.UserResponse;
import com.everyschool.schoolservice.domain.schoolapply.ApplyType;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolApplyResponse {

    private Long schoolApplyId;
    private String applyType;
    private String parentName;
    private String parentBirth;
    private String studentInfo;
    private String studentBirth;
    private LocalDateTime lastModifiedDate;

    @Builder
    public SchoolApplyResponse(Long schoolApplyId, String applyType, String parentName, String parentBirth, String studentInfo, String studentBirth, LocalDateTime lastModifiedDate) {
        this.schoolApplyId = schoolApplyId;
        this.applyType = applyType;
        this.parentName = parentName;
        this.parentBirth = parentBirth;
        this.studentInfo = studentInfo;
        this.studentBirth = studentBirth;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static SchoolApplyResponse of(SchoolApply schoolApply) {
        return SchoolApplyResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .applyType(ApplyType.getText(schoolApply.getCodeId()))
            .studentInfo(schoolApply.getStudentInfo())
            .lastModifiedDate(schoolApply.getLastModifiedDate())
            .build();
    }

    public static SchoolApplyResponse of(SchoolApply schoolApply, UserResponse student, UserResponse parent) {
        return SchoolApplyResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .applyType(ApplyType.getText(schoolApply.getCodeId()))
            .parentName(parent == null ? "" : parent.getName())
            .parentBirth(parent == null ? "" : parent.getBirth())
            .studentInfo(schoolApply.getStudentInfo())
            .studentBirth(student.getBirth())
            .lastModifiedDate(schoolApply.getLastModifiedDate())
            .build();
    }
}
