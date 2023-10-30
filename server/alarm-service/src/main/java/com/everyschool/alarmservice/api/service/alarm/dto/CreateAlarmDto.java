package com.everyschool.alarmservice.api.service.alarm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CreateAlarmDto {

    private String title;
    private String content;
    private Integer schoolYear;
    private List<String> recipientUserKeys;

    @Builder
    public CreateAlarmDto(String title, String content, Integer schoolYear, List<String> recipientUserKeys) {
        this.title = title;
        this.content = content;
        this.schoolYear = schoolYear;
        this.recipientUserKeys = recipientUserKeys;
    }
}
