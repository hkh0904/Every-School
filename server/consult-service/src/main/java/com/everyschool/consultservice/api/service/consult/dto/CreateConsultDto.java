package com.everyschool.consultservice.api.service.consult.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConsultDto {

    private LocalDateTime consultDateTime;
    private String message;
    private Integer schoolYear;
    private Integer typeId;
    private String teacherKey;
    private String studentKey;

    @Builder
    private CreateConsultDto(LocalDateTime consultDateTime, String message, Integer schoolYear, Integer typeId, String teacherKey, String studentKey) {
        this.consultDateTime = consultDateTime;
        this.message = message;
        this.schoolYear = schoolYear;
        this.typeId = typeId;
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
    }
}
