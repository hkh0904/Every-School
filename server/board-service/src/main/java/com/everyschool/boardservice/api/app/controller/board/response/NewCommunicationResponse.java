package com.everyschool.boardservice.api.app.controller.board.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewCommunicationResponse {

    private Long boardId;
    private String title;
    private LocalDateTime createdDate;

    @Builder
    public NewCommunicationResponse(Long boardId, String title, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.createdDate = createdDate;
    }
}
