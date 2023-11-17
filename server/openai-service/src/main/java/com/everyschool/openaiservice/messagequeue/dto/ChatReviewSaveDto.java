package com.everyschool.openaiservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatReviewSaveDto {

    private Long chatRoomId;
    private LocalDateTime chatDate;
    private String title;

    @Builder
    private ChatReviewSaveDto(Long chatRoomId, LocalDateTime chatDate, String title) {
        this.chatRoomId = chatRoomId;
        this.chatDate = chatDate;
        this.title = title;
    }
}
