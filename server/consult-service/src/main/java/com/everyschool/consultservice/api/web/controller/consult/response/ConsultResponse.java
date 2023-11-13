package com.everyschool.consultservice.api.web.controller.consult.response;

import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultResponse {

    private Long consultId;
    private String type;
    private String status;
    private String parentInfo;
    private LocalDateTime lastModifiedDate;
    private String rejectedReason;

    @Builder
    public ConsultResponse(Long consultId, int type, int status, String parentInfo, LocalDateTime lastModifiedDate, String rejectedReason) {
        this.consultId = consultId;
        this.type = ConsultType.getText(type);
        this.status = ProgressStatus.getText(status);
        this.parentInfo = parentInfo;
        this.lastModifiedDate = lastModifiedDate;
        this.rejectedReason = rejectedReason;
    }
}
