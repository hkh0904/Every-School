package com.everyschool.alarmservice.api.alarm.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendAlarmResponse {

    private Long alarmId;
    private String title;
    private String content;
    private int successSendCount;
    private LocalDateTime sendDate;

    @Builder
    public SendAlarmResponse(Long alarmId, String title, String content, int successSendCount, LocalDateTime sendDate) {
        this.alarmId = alarmId;
        this.title = title;
        this.content = content;
        this.successSendCount = successSendCount;
        this.sendDate = sendDate;
    }
}
