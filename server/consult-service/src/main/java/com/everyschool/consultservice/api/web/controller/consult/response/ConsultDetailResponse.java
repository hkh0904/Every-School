package com.everyschool.consultservice.api.web.controller.consult.response;

import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultDetailResponse {

    private Long consultId;
    private String type;
    private String status;
    private String parentInfo;
    private LocalDateTime consultDate;
    private String message;
    private String resultContent;
    private String rejectedReason;
    private LocalDateTime lastModifiesDate;

    @Builder
    public ConsultDetailResponse(Long consultId, int type, int status, String parentInfo, LocalDateTime consultDate, String message, String resultContent, String rejectedReason, LocalDateTime lastModifiesDate) {
        this.consultId = consultId;
        this.type = ConsultType.getText(type);
        this.status = ProgressStatus.getText(status);
        this.parentInfo = parentInfo;
        this.consultDate = consultDate;
        this.message = message;
        this.resultContent = resultContent;
        this.rejectedReason = rejectedReason;
        this.lastModifiesDate = lastModifiesDate;
    }
}
