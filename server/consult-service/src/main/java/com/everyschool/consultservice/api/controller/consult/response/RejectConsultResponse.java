package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RejectConsultResponse {

    private Long consultId;
    private LocalDateTime consultDateTime;
    private String progressStatus;
    private String type;
    private String rejectedReason;

    @Builder
    private RejectConsultResponse(Long consultId, LocalDateTime consultDateTime, int progressStatusId, int typeId, String rejectedReason) {
        this.consultId = consultId;
        this.consultDateTime = consultDateTime;
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.type = ConsultType.getText(typeId);
        this.rejectedReason = rejectedReason;
    }

    public static RejectConsultResponse of(Consult consult) {
        return RejectConsultResponse.builder()
            .consultId(consult.getId())
            .consultDateTime(consult.getConsultDateTime())
            .progressStatusId(consult.getProgressStatusId())
            .typeId(consult.getTypeId())
            .rejectedReason(consult.getRejectedReason())
            .build();
    }
}
