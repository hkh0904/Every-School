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
    private String type;
    private String progressStatus;
    private String title;
    private String message;
    private String resultContent;
    private String rejectedReason;
    private LocalDateTime consultDate;

    @Builder
    public ConsultDetailResponse(Long consultId, int typeId, int progressStatusId, String title, String message, String resultContent, String rejectedReason, LocalDateTime consultDate) {
        this.consultId = consultId;
        this.type = ConsultType.getText(typeId);
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.title = title;
        this.message = message;
        this.resultContent = resultContent;
        this.rejectedReason = rejectedReason;
        this.consultDate = consultDate;
    }

    public static ConsultDetailResponse of(Consult consult) {
        return ConsultDetailResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .progressStatusId(consult.getProgressStatusId())
            .title(consult.getTitle().getParentTitle())
            .message(consult.getMessage())
            .resultContent(consult.getResultContent())
            .rejectedReason(consult.getRejectedReason())
            .consultDate(consult.getConsultDateTime())
            .build();
    }
}
