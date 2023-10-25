package com.everyschool.consultservice.api.controller.consult.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateConsultScheduleRequest {

    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    private CreateConsultScheduleRequest(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
