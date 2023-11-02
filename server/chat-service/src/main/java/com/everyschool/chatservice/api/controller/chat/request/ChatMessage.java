package com.everyschool.chatservice.api.controller.chat.request;

import com.everyschool.chatservice.api.service.chat.dto.SendMessageDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    private Long chatRoomId;
    private String senderUserKey;
    private String message;

    @Builder
    private ChatMessage(Long chatRoomId, String senderUserKey, String message) {
        this.chatRoomId = chatRoomId;
        this.senderUserKey = senderUserKey;
        this.message = message;
    }

    public SendMessageDto toDto() {
        return SendMessageDto.builder()
                .chatRoomId(this.chatRoomId)
                .senderUserKey(this.senderUserKey)
                .message(this.message)
                .build();
    }
}