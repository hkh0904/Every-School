package com.everyschool.consultservice.api.controller.consult.request;

import com.everyschool.consultservice.api.service.consult.dto.CreateConsultDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateConsultRequest {

    private LocalDateTime consultDateTime;
    private String message;
    private Integer schoolYear;
    private Integer typeId;
    private String teacherKey;
    private String studentKey;

    @Builder
    private CreateConsultRequest(LocalDateTime consultDateTime, String message, Integer schoolYear, Integer typeId, String teacherKey, String studentKey) {
        this.consultDateTime = consultDateTime;
        this.message = message;
        this.schoolYear = schoolYear;
        this.typeId = typeId;
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
    }

    public CreateConsultDto toDto() {
        return CreateConsultDto.builder()
            .consultDateTime(this.consultDateTime)
            .message(this.message)
            .schoolYear(this.schoolYear)
            .typeId(this.typeId)
            .teacherKey(this.teacherKey)
            .studentKey(this.studentKey)
            .build();
    }
}
