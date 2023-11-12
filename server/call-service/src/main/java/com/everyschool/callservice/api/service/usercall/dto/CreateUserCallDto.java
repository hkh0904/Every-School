package com.everyschool.callservice.api.service.usercall.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserCallDto {
    private Long otherUserId;
    private Long teacherId;
    private String sender;
    private String senderName;
    private String receiverName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isBad;

    @Builder
    private CreateUserCallDto(Long otherUserId, Long teacherId, String sender, String senderName, String receiverName,
                              LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean isBad) {
        this.otherUserId = otherUserId;
        this.teacherId = teacherId;
        this.sender = sender;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isBad = isBad;
    }
}
