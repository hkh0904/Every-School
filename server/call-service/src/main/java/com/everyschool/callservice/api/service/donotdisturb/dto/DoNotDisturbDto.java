package com.everyschool.callservice.api.service.donotdisturb.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoNotDisturbDto {

    private Long teacherId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActivate;

    @Builder
    private DoNotDisturbDto(LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActivate = isActivate;
    }
}
