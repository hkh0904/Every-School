package com.everyschool.alarmservice.api.service.alarm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CreateAlarmDto {

    private String title;
    private String content;
    private String type;
    private Integer schoolYear;
    private List<String> recipientUserKeys;

    @Builder
    public CreateAlarmDto(String title, String content, String type, Integer schoolYear, List<String> recipientUserKeys) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.schoolYear = schoolYear;
        this.recipientUserKeys = recipientUserKeys;
    }
}
