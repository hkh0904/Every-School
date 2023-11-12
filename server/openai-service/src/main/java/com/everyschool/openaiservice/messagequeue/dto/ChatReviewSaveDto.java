package com.everyschool.openaiservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ChatReviewSaveDto {

    private Long chatRoomId;
    private LocalDate chatDate;
    private String title;

    @Builder
    private ChatReviewSaveDto(Long chatRoomId, LocalDate chatDate, String title) {
        this.chatRoomId = chatRoomId;
        this.chatDate = chatDate;
        this.title = title;
    }
}
