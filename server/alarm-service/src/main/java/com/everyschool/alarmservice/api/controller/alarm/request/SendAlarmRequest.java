package com.everyschool.alarmservice.api.controller.alarm.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SendAlarmRequest {

    private String title;
    private String content;
    private Integer year;
    private List<String> userKeys;

    @Builder
    private SendAlarmRequest(String title, String content, Integer year, List<String> userKeys) {
        this.title = title;
        this.content = content;
        this.year = year;
        this.userKeys = userKeys;
    }
}
