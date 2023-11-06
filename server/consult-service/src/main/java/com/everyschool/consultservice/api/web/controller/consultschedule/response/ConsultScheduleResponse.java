package com.everyschool.consultservice.api.web.controller.consultschedule.response;

import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
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
    private LocalDateTime lastModifiedDate;

    @Builder
    private ConsultScheduleResponse(Long consultScheduleId, String description, List<Boolean> monday, List<Boolean> tuesday, List<Boolean> wednesday, List<Boolean> thursday, List<Boolean> friday, LocalDateTime lastModifiedDate) {
        this.consultScheduleId = consultScheduleId;
        this.description = description;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static ConsultScheduleResponse of(ConsultSchedule consultSchedule) {
        return ConsultScheduleResponse.builder()
            .consultScheduleId(consultSchedule.getId())
            .description(consultSchedule.getDescription())
            .monday(change(consultSchedule.getMonday()))
            .tuesday(change(consultSchedule.getTuesday()))
            .wednesday(change(consultSchedule.getWednesday()))
            .thursday(change(consultSchedule.getThursday()))
            .friday(change(consultSchedule.getFriday()))
            .lastModifiedDate(consultSchedule.getLastModifiedDate())
            .build();
    }

    private static List<Boolean> change(String data) {
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
