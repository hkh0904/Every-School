package com.everyschool.openaiservice.api.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Chat {

    private Long id;

    private Long userId;
    private String content;
    private int status;

    private Long chatRoomId;

    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    private Chat(Long id, Long userId, String content, int status, Long chatRoomId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.status = status;
        this.chatRoomId = chatRoomId;
        this.isDeleted = false;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }
}
