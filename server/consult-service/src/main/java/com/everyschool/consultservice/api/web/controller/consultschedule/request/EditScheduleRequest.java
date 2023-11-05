package com.everyschool.consultservice.api.web.controller.consultschedule.request;

import com.everyschool.consultservice.api.web.service.consultschedule.dto.EditScheduleDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EditScheduleRequest {

    private List<Boolean> monday;
    private List<Boolean> tuesday;
    private List<Boolean> wednesday;
    private List<Boolean> thursday;
    private List<Boolean> friday;

    @Builder
    private EditScheduleRequest(List<Boolean> monday, List<Boolean> tuesday, List<Boolean> wednesday, List<Boolean> thursday, List<Boolean> friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public EditScheduleDto toDto() {
        return EditScheduleDto.builder()
            .monday(this.monday)
            .tuesday(this.tuesday)
            .wednesday(this.wednesday)
            .thursday(this.thursday)
            .friday(this.friday)
            .build();
    }
}
