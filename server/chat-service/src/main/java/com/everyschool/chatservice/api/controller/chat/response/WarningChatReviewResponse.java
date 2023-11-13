package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WarningChatReviewResponse {

    private Long chatReviewId;
    private Long chatRoomId;
    private LocalDate chatDate;

    @Builder
    public WarningChatReviewResponse(Long chatReviewId, Long chatRoomId, LocalDate chatDate) {
        this.chatReviewId = chatReviewId;
        this.chatRoomId = chatRoomId;
        this.chatDate = chatDate;
    }
}
