package com.everyschool.consultservice.api.app.controller.consult.response;

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
    private String teacherInfo;
    private String parentInfo;
    private LocalDateTime consultDateTime;
    private String message;
    private String resultContent;
    private String rejectReason;

    @Builder
    public ConsultDetailResponse(Long consultId, int type, int status, String teacherInfo, String parentInfo, LocalDateTime consultDateTime, String message, String resultContent, String rejectReason) {
        this.consultId = consultId;
        this.type = ConsultType.getText(type);
        this.status = ProgressStatus.getText(status);
        this.teacherInfo = teacherInfo;
        this.parentInfo = parentInfo;
        this.consultDateTime = consultDateTime;
        this.message = message;
        this.resultContent = resultContent;
        this.rejectReason = rejectReason;
    }
}
