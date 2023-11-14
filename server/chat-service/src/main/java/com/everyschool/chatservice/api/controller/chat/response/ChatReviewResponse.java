package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatReviewResponse {

    private String title;
    private Long chatRoomId;

    @Builder
    public ChatReviewResponse(String title, Long chatRoomId) {
        this.title = title;
        this.chatRoomId = chatRoomId;
    }
}
