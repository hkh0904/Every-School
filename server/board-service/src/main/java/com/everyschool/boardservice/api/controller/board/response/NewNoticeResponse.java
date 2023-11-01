package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewNoticeResponse {

    private Long boardId;
    private String title;
    private LocalDateTime createdDate;

    @Builder
    public NewNoticeResponse(Long boardId, String title, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.createdDate = createdDate;
    }
}
