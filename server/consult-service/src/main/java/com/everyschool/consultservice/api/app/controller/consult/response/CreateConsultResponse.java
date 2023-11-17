package com.everyschool.consultservice.api.app.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConsultResponse {

    private Long consultId;
    private String type;
    private String teacherInfo;
    private String parentInfo;
    private LocalDateTime consultDateTime;
    private String message;

    @Builder
    private CreateConsultResponse(Long consultId, int type, String teacherInfo, String parentInfo, LocalDateTime consultDateTime, String message) {
        this.consultId = consultId;
        this.type = ConsultType.getText(type);
        this.teacherInfo = teacherInfo;
        this.parentInfo = parentInfo;
        this.consultDateTime = consultDateTime;
        this.message = message;
    }

    public static CreateConsultResponse of(Consult consult) {
        return CreateConsultResponse.builder()
            .consultId(consult.getId())
            .type(consult.getTypeId())
            .teacherInfo(consult.getTitle().getTeacherTitle())
            .parentInfo(consult.getTitle().getParentTitle())
            .consultDateTime(consult.getConsultDateTime())
            .message(consult.getMessage())
            .build();
    }
}
