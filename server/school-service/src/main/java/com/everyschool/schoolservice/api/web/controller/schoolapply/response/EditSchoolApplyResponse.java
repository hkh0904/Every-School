package com.everyschool.schoolservice.api.web.controller.schoolapply.response;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditSchoolApplyResponse {

    private Long schoolApplyId;
    private Integer schoolYear;
    private Boolean isApproved;
    private String rejectedReason;
    private LocalDateTime lastModifiedDate;

    @Builder
    private EditSchoolApplyResponse(Long schoolApplyId, Integer schoolYear, Boolean isApproved, String rejectedReason, LocalDateTime lastModifiedDate) {
        this.schoolApplyId = schoolApplyId;
        this.schoolYear = schoolYear;
        this.isApproved = isApproved;
        this.rejectedReason = rejectedReason;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static EditSchoolApplyResponse of(SchoolApply schoolApply) {
        return EditSchoolApplyResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .schoolYear(schoolApply.getSchoolYear())
            .isApproved(schoolApply.getIsApproved())
            .rejectedReason(schoolApply.getRejectedReason())
            .lastModifiedDate(schoolApply.getLastModifiedDate())
            .build();
    }
}
