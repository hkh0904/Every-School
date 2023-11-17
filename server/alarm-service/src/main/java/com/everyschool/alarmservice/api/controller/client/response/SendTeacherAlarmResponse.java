package com.everyschool.alarmservice.api.controller.client.response;

import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendTeacherAlarmResponse {

    private Long alarmId;
    private String title;
    private String content;
    private String type;
    private int successSendCount;
    private LocalDateTime sendDate;

    @Builder
    public SendTeacherAlarmResponse(Long alarmId, String title, String type, String content, int successSendCount, LocalDateTime sendDate) {
        this.alarmId = alarmId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.successSendCount = successSendCount;
        this.sendDate = sendDate;
    }

    public static SendTeacherAlarmResponse of(AlarmMaster alarmMaster) {
        return SendTeacherAlarmResponse.builder()
            .alarmId(alarmMaster.getId())
            .title(alarmMaster.getTitle())
            .content(alarmMaster.getContent())
            .type(alarmMaster.getType())
            .successSendCount(alarmMaster.getAlarms().size())
            .sendDate(alarmMaster.getCreatedDate())
            .build();
    }
}
