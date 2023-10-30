package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ConsultType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConsultResponse {

    private Long consultId;
    private String type;
    private String teacher;
    private String applicant;
    private LocalDateTime consultDateTime;
    private String message;

    @Builder
    public CreateConsultResponse(Long consultId, int typeId, String teacher, String applicant, LocalDateTime consultDateTime, String message) {
        this.consultId = consultId;
        this.type = ConsultType.getText(typeId);
        this.teacher = teacher;
        this.applicant = applicant;
        this.consultDateTime = consultDateTime;
        this.message = message;
    }

    public static CreateConsultResponse of(Consult consult, UserInfo userInfo, SchoolClassInfo schoolClassInfo) {
        return CreateConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .teacher(String.format("%d학년 %d반 %s 선생님", schoolClassInfo.getGrade(), schoolClassInfo.getClassNum(), userInfo.getUserName()))
            .applicant("none")
            .consultDateTime(consult.getConsultDateTime())
            .message(consult.getMessage())
            .build();
    }
}
