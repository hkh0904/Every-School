package com.everyschool.consultservice.api.web.service.consultschedule.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class EditScheduleDto {

    private List<Boolean> monday;
    private List<Boolean> tuesday;
    private List<Boolean> wednesday;
    private List<Boolean> thursday;
    private List<Boolean> friday;

    @Builder
    private EditScheduleDto(List<Boolean> monday, List<Boolean> tuesday, List<Boolean> wednesday, List<Boolean> thursday, List<Boolean> friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
}
