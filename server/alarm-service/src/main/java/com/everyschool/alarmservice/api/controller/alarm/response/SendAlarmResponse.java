package com.everyschool.alarmservice.api.controller.alarm.response;

import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
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

    public static SendAlarmResponse of(AlarmMaster alarmMaster) {
        return SendAlarmResponse.builder()
            .alarmId(alarmMaster.getId())
            .title(alarmMaster.getTitle())
            .content(alarmMaster.getContent())
            .successSendCount(alarmMaster.getAlarms().size())
            .sendDate(alarmMaster.getCreatedDate())
            .build();
    }
}
