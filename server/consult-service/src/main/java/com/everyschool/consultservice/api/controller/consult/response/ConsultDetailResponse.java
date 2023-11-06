package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultDetailResponse {

    private Long consultId;
    private Integer schoolYear;
    private String type;
    private String status;
    private String title;
    private String message;
    private String resultContent;
    private String rejectedReason;
    private LocalDateTime consultDateTime;
    private LocalDateTime createdDate;

    @Builder
    private ConsultDetailResponse(Long consultId, Integer schoolYear, int typeId, int statusId, String title, String message, String resultContent, String rejectedReason, LocalDateTime consultDateTime, LocalDateTime createdDate) {
        this.consultId = consultId;
        this.schoolYear = schoolYear;
        this.type = ConsultType.getText(typeId);
        this.status = ProgressStatus.getText(statusId);
        this.title = title;
        this.message = message;
        this.resultContent = resultContent;
        this.rejectedReason = rejectedReason;
        this.consultDateTime = consultDateTime;
        this.createdDate = createdDate;
    }

    public static ConsultDetailResponse of(Consult consult) {
        return ConsultDetailResponse.builder()
            .consultId(consult.getId())
            .schoolYear(consult.getSchoolYear())
            .typeId(consult.getTypeId())
            .statusId(consult.getProgressStatusId())
            .title(consult.getTitle().getTeacherTitle())
            .message(consult.getMessage())
            .resultContent(consult.getResultContent())
            .rejectedReason(consult.getRejectedReason())
            .consultDateTime(consult.getConsultDateTime())
            .createdDate(consult.getCreatedDate())
            .build();
    }
}
