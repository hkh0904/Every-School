package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultResponse {

    private Long consultId;
    private String type;
    private String studentInfo;
    private String parentInfo;
    private LocalDateTime consultDateTime;

    @Builder
    private ConsultResponse(Long consultId, int typeId, String studentInfo, String parentInfo, LocalDateTime consultDateTime) {
        this.consultId = consultId;
        this.type = ConsultType.getText(typeId);
        this.studentInfo = studentInfo;
        this.parentInfo = parentInfo;
        this.consultDateTime = consultDateTime;
    }

    public static ConsultResponse of(Consult consult, String studentInfo, String parentInfo) {
        return ConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .studentInfo(studentInfo)
            .parentInfo(parentInfo)
            .consultDateTime(consult.getConsultDateTime())
            .build();
    }
}
