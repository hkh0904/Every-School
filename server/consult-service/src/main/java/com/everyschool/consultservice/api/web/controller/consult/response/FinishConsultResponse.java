package com.everyschool.consultservice.api.web.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FinishConsultResponse {

    private Long consultId;
    private LocalDateTime consultDateTime;
    private String progressStatus;
    private String type;
    private String resultContent;

    @Builder
    private FinishConsultResponse(Long consultId, LocalDateTime consultDateTime, int progressStatusId, int typeId, String resultContent) {
        this.consultId = consultId;
        this.consultDateTime = consultDateTime;
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.type = ConsultType.getText(typeId);
        this.resultContent = resultContent;
    }

    public static FinishConsultResponse of(Consult consult) {
        return FinishConsultResponse.builder()
            .consultId(consult.getId())
            .consultDateTime(consult.getConsultDateTime())
            .progressStatusId(consult.getProgressStatusId())
            .typeId(consult.getTypeId())
            .resultContent(consult.getResultContent())
            .build();
    }
}
