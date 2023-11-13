package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WarningChatReviewResponse {

    private Long chatRoomId;
    private Long reportId;
    private String type;
    private String reportedName;
    private LocalDate reportedDate;

    @Builder
    public WarningChatReviewResponse(Long chatReviewId, Long chatRoomId, LocalDate chatDate, String roomTitle) {
        this.reportId = chatReviewId;
        this.chatRoomId = chatRoomId;
        this.type = "채팅";
        this.reportedDate = chatDate;
        this.reportedName = roomTitle;
    }
}
