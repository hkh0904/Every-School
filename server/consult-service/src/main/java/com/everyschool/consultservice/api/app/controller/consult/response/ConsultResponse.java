package com.everyschool.consultservice.api.app.controller.consult.response;

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
    private String info;
    private LocalDateTime consultDateTime;

    @Builder
    public ConsultResponse(Long consultId, int type, int status, String info, LocalDateTime consultDateTime) {
        this.consultId = consultId;
        this.type = ConsultType.getText(type);
        this.status = ProgressStatus.getText(status);
        this.info = info;
        this.consultDateTime = consultDateTime;
    }
}
