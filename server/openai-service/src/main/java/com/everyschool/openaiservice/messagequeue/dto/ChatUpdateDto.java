package com.everyschool.openaiservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatUpdateDto {
    private Long chatId;
    private String reason;

    @Builder
    private ChatUpdateDto(Long chatId, String reason) {
        this.chatId = chatId;
        this.reason = reason;
    }
}
