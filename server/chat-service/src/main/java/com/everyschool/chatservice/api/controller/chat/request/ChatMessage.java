package com.everyschool.chatservice.api.controller.chat.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    private Long chatRoomId;
    private Long senderUserKey;
    private String message;

    @Builder
    private ChatMessage(Long chatRoomId, Long senderUserKey, String message) {
        this.chatRoomId = chatRoomId;
        this.senderUserKey = senderUserKey;
        this.message = message;
    }

    public String getRoomTopic() {
        return "CHATROOM_TOPIC_" + this.chatRoomId;
    }
}
