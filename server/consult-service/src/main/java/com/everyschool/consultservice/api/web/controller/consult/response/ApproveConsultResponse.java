package com.everyschool.consultservice.api.web.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApproveConsultResponse {

    private Long consultId;
    private String type;
    private String progressStatus;
    private LocalDateTime consultDateTime;

    @Builder
    private ApproveConsultResponse(Long consultId, LocalDateTime consultDateTime, int progressStatusId, int typeId) {
        this.consultId = consultId;
        this.type = ConsultType.getText(typeId);
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.consultDateTime = consultDateTime;
    }

    public static ApproveConsultResponse of(Consult consult) {
        return ApproveConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .progressStatusId(consult.getProgressStatusId())
            .consultDateTime(consult.getConsultDateTime())
            .build();
    }
}
