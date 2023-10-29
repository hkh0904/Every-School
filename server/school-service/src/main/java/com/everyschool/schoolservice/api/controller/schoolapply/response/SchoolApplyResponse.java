package com.everyschool.schoolservice.api.controller.schoolapply.response;

import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolApplyResponse {

    private Long schoolApplyId; //schoolApply
    private String type; //부모 fk 없으면 학생 신청, 있으면 학부모 신청
    private String childInfo; //user service 조회
    private String relationship; //부모 fk 없으면 본인, 아니면 조회...?
    private LocalDateTime appliedDate; //schoolApply

    @Builder
    public SchoolApplyResponse(Long schoolApplyId, Long parentId, String childInfo, LocalDateTime appliedDate) {
        this.schoolApplyId = schoolApplyId;
        this.type = parentId == null ? "학생 신청" : "학부모 신청";
        this.childInfo = childInfo;
        this.relationship = parentId == null ? "본인" : "학부모";;
        this.appliedDate = appliedDate;
    }

    public static SchoolApplyResponse of(SchoolApply schoolApply, StudentResponse info) {
        return SchoolApplyResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .childInfo(String.format("%d학년 %d반 %s", schoolApply.getSchoolClass().getGrade(), schoolApply.getSchoolClass().getClassNum(), info.getName()))
            .parentId(schoolApply.getParentId())
            .appliedDate(schoolApply.getCreatedDate())
            .build();
    }
}
