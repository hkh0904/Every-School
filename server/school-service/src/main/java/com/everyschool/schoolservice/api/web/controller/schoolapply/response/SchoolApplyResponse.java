package com.everyschool.schoolservice.api.web.controller.schoolapply.response;

import com.everyschool.schoolservice.domain.schoolapply.ApplyType;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolApplyResponse {

    private Long schoolApplyId;
    private String applyType;
    private String studentInfo;
    private LocalDateTime lastModifiedDate;

    @Builder
    public SchoolApplyResponse(Long schoolApplyId, String applyType, String studentInfo, LocalDateTime lastModifiedDate) {
        this.schoolApplyId = schoolApplyId;
        this.applyType = applyType;
        this.studentInfo = studentInfo;
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
}
