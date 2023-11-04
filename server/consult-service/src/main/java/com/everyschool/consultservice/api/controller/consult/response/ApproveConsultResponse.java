package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApproveConsultResponse {

    private Long consultId;
    private LocalDateTime consultDateTime;
    private String progressStatus;
    private String type;

    @Builder
    private ApproveConsultResponse(Long consultId, LocalDateTime consultDateTime, int progressStatusId, int typeId) {
        this.consultId = consultId;
        this.consultDateTime = consultDateTime;
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.type = ConsultType.getText(typeId);
    }

    public static ApproveConsultResponse of(Consult consult) {
        return ApproveConsultResponse.builder()
            .consultId(consult.getId())
            .consultDateTime(consult.getConsultDateTime())
            .progressStatusId(consult.getProgressStatusId())
            .typeId(consult.getTypeId())
            .build();
    }
}
