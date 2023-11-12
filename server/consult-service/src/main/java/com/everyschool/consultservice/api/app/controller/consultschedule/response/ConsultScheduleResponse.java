package com.everyschool.consultservice.api.app.controller.consultschedule.response;

import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConsultScheduleResponse {

    private Long consultScheduleId;
    private String description;
    private List<Boolean> monday;
    private List<Boolean> tuesday;
    private List<Boolean> wednesday;
    private List<Boolean> thursday;
    private List<Boolean> friday;

    @Builder
    private ConsultScheduleResponse(Long consultScheduleId, String description, List<Boolean> monday, List<Boolean> tuesday, List<Boolean> wednesday, List<Boolean> thursday, List<Boolean> friday) {
        this.consultScheduleId = consultScheduleId;
        this.description = description;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public static ConsultScheduleResponse of(ConsultSchedule consultSchedule) {
        return ConsultScheduleResponse.builder()
            .consultScheduleId(consultSchedule.getId())
            .description(consultSchedule.getDescription())
            .monday(convert(consultSchedule.getMonday()))
            .tuesday(convert(consultSchedule.getTuesday()))
            .wednesday(convert(consultSchedule.getWednesday()))
            .thursday(convert(consultSchedule.getThursday()))
            .friday(convert(consultSchedule.getFriday()))
            .build();
    }

    private static List<Boolean> convert(String data) {
        List<Boolean> temp = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == '0') {
                temp.add(false);
                continue;
            }
            temp.add(true);
        }
        return temp;
    }
}
