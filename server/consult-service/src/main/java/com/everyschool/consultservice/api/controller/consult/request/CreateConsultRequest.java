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
    private Integer typeId;
    private String teacherKey;
    private String studentKey;

    @Builder
    private CreateConsultRequest(LocalDateTime consultDateTime, String message, Integer schoolYear, Integer typeId, String teacherKey, String studentKey) {
        this.consultDateTime = consultDateTime;
        this.message = message;
        this.typeId = typeId;
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
    }

    public CreateConsultDto toDto() {
        return CreateConsultDto.builder()
            .consultDateTime(this.consultDateTime)
            .message(this.message)
            // TODO: 10/30/23 수정이 필요
            .schoolYear(LocalDateTime.now().getYear())
            .typeId(this.typeId)
            .teacherKey(this.teacherKey)
            .studentKey(this.studentKey)
            .build();
    }
}
