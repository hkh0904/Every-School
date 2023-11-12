package com.everyschool.schoolservice.api.app.controller.schoolapply.response;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSchoolApplyResponse {

    private Long schoolApplyId;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;
    private LocalDateTime appliedDate;

    @Builder
    private CreateSchoolApplyResponse(Long schoolApplyId, Integer schoolYear, Integer grade, Integer classNum, LocalDateTime appliedDate) {
        this.schoolApplyId = schoolApplyId;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
        this.appliedDate = appliedDate;
    }

    public static CreateSchoolApplyResponse of(SchoolApply schoolApply) {
        return CreateSchoolApplyResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .schoolYear(schoolApply.getSchoolClass().getSchoolYear())
            .grade(schoolApply.getSchoolClass().getGrade())
            .classNum(schoolApply.getSchoolClass().getClassNum())
            .appliedDate(schoolApply.getCreatedDate())
            .build();
    }
}
