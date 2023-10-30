package com.everyschool.consultservice.api.controller.consult.response;

import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.TeacherInfo;
import com.everyschool.consultservice.domain.consult.Consult;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultDetailResponse {

    private Long consultId;
    private String type;
    private String progressStatus;
    private String title;
    private String message;
    private String resultContent;
    private String rejectedReason;
    private LocalDateTime consultDate;

    @Builder
    public ConsultDetailResponse(Long consultId, int typeId, int progressStatusId, String title, String message, String resultContent, String rejectedReason, LocalDateTime consultDate) {
        this.consultId = consultId;
        this.type = getType(typeId);
        this.progressStatus = getProgressStatus(progressStatusId);
        this.title = title;
        this.message = message;
        this.resultContent = resultContent;
        this.rejectedReason = rejectedReason;
        this.consultDate = consultDate;
    }

    public static ConsultDetailResponse of(Consult consult, TeacherInfo teacherInfo, SchoolClassInfo schoolClassInfo) {
        return ConsultDetailResponse.builder()
            .consultId(consult.getId())
            .typeId(consult.getTypeId())
            .progressStatusId(consult.getProgressStatusId())
            .title(String.format("%d학년 %d반 %s선생님", schoolClassInfo.getGrade(), schoolClassInfo.getClassNum(), teacherInfo.getName()))
            .message(consult.getMessage())
            .resultContent(consult.getResultContent())
            .rejectedReason(consult.getRejectedReason())
            .consultDate(consult.getConsultDateTime())
            .build();
    }

    public String getType(int typeId) {
        if (typeId == 2001) {
            return "방문상담";
        }
        return "전화상담";
    }

    private String getProgressStatus(int progressStatusId) {
        if (progressStatusId == 3001) {
            return "승인 대기중";
        }
        if (progressStatusId == 3002) {
            return "예약 완료";
        }
        if (progressStatusId == 3003) {
            return "상담 완료";
        }
        return "승인 거절";
    }
}
