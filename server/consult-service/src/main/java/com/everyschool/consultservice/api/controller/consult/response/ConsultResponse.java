package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultResponse {

    private Long consultId;
    private String type;
    private String progressStatus;
    private String title;
    private LocalDateTime consultDate;

    @Builder
    public ConsultResponse(Long consultId, int typeId, int progressStatusId, String title, LocalDateTime consultDate) {
        this.consultId = consultId;
        this.type = ConsultType.getText(typeId);
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.title = title;
        this.consultDate = consultDate;
    }

    public static ConsultResponse of(Consult consult, char userType) {
        return ConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .progressStatusId(consult.getProgressStatusId())
            .title(userType == 'T' ? consult.getTitle().getTeacherTitle() : consult.getTitle().getParentTitle())
            .consultDate(consult.getConsultDateTime())
            .build();
    }
}
