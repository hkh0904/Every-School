package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.domain.consult.Consult;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.everyschool.consultservice.domain.consult.ConsultType.getText;

@Data
public class WaitConsultResponse {

    private Long consultId;
    private String type;
    private String studentInfo;
    private String parentInfo;
    private LocalDateTime consultDate;

    @Builder
    public WaitConsultResponse(Long consultId, int typeId, String studentInfo, String parentInfo, LocalDateTime consultDate) {
        this.consultId = consultId;
        this.type = getText(typeId);
        this.studentInfo = studentInfo;
        this.parentInfo = parentInfo;
        this.consultDate = consultDate;
    }

    public static WaitConsultResponse of(Consult consult, String studentInfo, String parentInfo) {
        return WaitConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .studentInfo(studentInfo)
            .parentInfo(parentInfo)
            .consultDate(consult.getConsultDateTime())
            .build();
    }
}
