package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WarningChat {

    private Long chatId;
    private boolean teacherSend;
    private String content;
    private LocalDateTime sendTime;
    private String chatStatus;
    private String reason;

    @Builder
    public WarningChat(Long chatId, boolean teacherSend, String content, LocalDateTime sendTime, String chatStatus, String reason) {
        this.chatId = chatId;
        this.teacherSend = teacherSend;
        this.content = content;
        this.sendTime = sendTime;
        this.chatStatus = chatStatus;
        this.reason = reason;
    }
}
