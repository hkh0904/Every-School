package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.TeacherInfo;
import com.everyschool.consultservice.domain.consult.Consult;
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
        this.type = getType(typeId);
        this.teacher = teacher;
        this.applicant = applicant;
        this.consultDateTime = consultDateTime;
        this.message = message;
    }

    public static CreateConsultResponse of(Consult consult, TeacherInfo teacherInfo, SchoolClassInfo schoolClassInfo) {
        return CreateConsultResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .teacher(String.format("%d학년 %d반 %s 선생님", schoolClassInfo.getGrade(), schoolClassInfo.getClassNum(), teacherInfo.getName()))
            .applicant("none")
            .consultDateTime(consult.getConsultDateTime())
            .message(consult.getMessage())
            .build();
    }

    private String getType(int typeId) {
        if (typeId == 2001) {
            return "방문상담";
        }
        return "전화상담";
    }
}
