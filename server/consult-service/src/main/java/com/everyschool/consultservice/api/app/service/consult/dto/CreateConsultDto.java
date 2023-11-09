package com.everyschool.consultservice.api.app.service.consult.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConsultDto {

    private String teacherKey;
    private String studentKey;
    private Integer typeId;
    private LocalDateTime consultDateTime;
    private String message;

    @Builder
    private CreateConsultDto(String teacherKey, String studentKey, Integer typeId, LocalDateTime consultDateTime, String message) {
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
        this.typeId = typeId;
        this.consultDateTime = consultDateTime;
        this.message = message;
    }
}
