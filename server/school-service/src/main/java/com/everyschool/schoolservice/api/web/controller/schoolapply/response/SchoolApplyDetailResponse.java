package com.everyschool.schoolservice.api.web.controller.schoolapply.response;

import com.everyschool.schoolservice.domain.schoolapply.ApplyType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolApplyDetailResponse {

    private Long schoolApplyId;
    private String applyType;
    private String parentName;
    private String parentBirth;
    private String studentInfo;
    private String studentBirth;
    private LocalDateTime lastModifiedDate;

    @Builder
    public SchoolApplyDetailResponse(Long schoolApplyId, int applyType, String parentName, String parentBirth, String studentInfo, String studentBirth, LocalDateTime lastModifiedDate) {
        this.schoolApplyId = schoolApplyId;
        this.applyType = ApplyType.getText(applyType);
        this.parentName = parentName;
        this.parentBirth = parentBirth;
        this.studentInfo = studentInfo;
        this.studentBirth = studentBirth;
        this.lastModifiedDate = lastModifiedDate;
    }
}
