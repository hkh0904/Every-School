package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatResponse {

    private Long chatId;
    private boolean isMine;
    private String content;
    private LocalDateTime sendTime;

    @Builder
    private ChatResponse(Long chatId, boolean isMine, String content, LocalDateTime sendTime) {
        this.chatId = chatId;
        this.isMine = isMine;
        this.content = content;
        this.sendTime = sendTime;
    }
}
