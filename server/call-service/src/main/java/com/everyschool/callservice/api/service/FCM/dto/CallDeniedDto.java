package com.everyschool.callservice.api.service.FCM.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallDeniedDto {

    private String otherUserKey;
    private String senderName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private CallDeniedDto(String otherUserKey, String senderName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
