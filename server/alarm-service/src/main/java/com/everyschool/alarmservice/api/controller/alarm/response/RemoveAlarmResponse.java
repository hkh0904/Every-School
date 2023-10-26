package com.everyschool.alarmservice.api.controller.alarm.response;

import com.everyschool.alarmservice.domain.alarm.Alarm;
import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemoveAlarmResponse {

    private String title;
    private String content;
    private Integer schoolYear;
    private LocalDateTime removedDate;

    @Builder
    public RemoveAlarmResponse(String title, String content, Integer schoolYear, LocalDateTime removedDate) {
        this.title = title;
        this.content = content;
        this.schoolYear = schoolYear;
        this.removedDate = removedDate;
    }

    public static RemoveAlarmResponse of(AlarmMaster alarmMaster) {
        return RemoveAlarmResponse.builder()
            .title(alarmMaster.getTitle())
            .content(alarmMaster.getContent())
            .schoolYear(alarmMaster.getSchoolYear())
            .removedDate(alarmMaster.getLastModifiedDate())
            .build();
    }

    public static RemoveAlarmResponse of(Alarm alarm) {
        return RemoveAlarmResponse.builder()
            .title(alarm.getAlarmMaster().getTitle())
            .content(alarm.getAlarmMaster().getContent())
            .schoolYear(alarm.getAlarmMaster().getSchoolYear())
            .removedDate(alarm.getLastModifiedDate())
            .build();
    }
}
