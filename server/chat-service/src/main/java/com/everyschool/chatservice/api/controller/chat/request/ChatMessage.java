package com.everyschool.chatservice.api.controller.chat.request;

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
}


// TODO: 2023-11-01 이거 임시 커밋함 해야함