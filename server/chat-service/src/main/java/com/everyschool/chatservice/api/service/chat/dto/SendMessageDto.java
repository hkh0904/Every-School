package com.everyschool.chatservice.api.service.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SendMessageDto {
    private Long chatRoomId;
    private String senderUserKey;
    private String message;

    @Builder
    private SendMessageDto(Long chatRoomId, String senderUserKey, String message) {
        this.chatRoomId = chatRoomId;
        this.senderUserKey = senderUserKey;
        this.message = message;
    }
}
