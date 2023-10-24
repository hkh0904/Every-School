package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatResponse {

    private Long chatId;
    private boolean isMine;
    private String content;
    private String sendTime;

    @Builder
    private ChatResponse(Long chatId, boolean isMine, String content, LocalDateTime sendTime) {
        this.chatId = chatId;
        this.isMine = isMine;
        this.content = content;
        this.sendTime = generateTime(sendTime);
    }

    private String generateTime(LocalDateTime sendTime) {
        if (sendTime.getHour() < 12) {
            return "오전 " + sendTime.getHour() + ":" + sendTime.getMinute();
        }
        return "오전 " + (sendTime.getHour() - 12) + ":" + sendTime.getMinute();
    }
}
