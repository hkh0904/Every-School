package com.everyschool.alarmservice.api.controller.alarm.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmResponse {

    private Long alarmId;
    private String sender; //외부 부입
    private String title;
    private String content;
    private Integer schoolYear;
    private LocalDateTime receivedDate;

    @Builder
    public AlarmResponse(Long alarmId, String title, String content, Integer schoolYear, LocalDateTime receivedDate) {
        this.alarmId = alarmId;
        this.title = title;
        this.content = content;
        this.schoolYear = schoolYear;
        this.receivedDate = receivedDate;
    }
}
