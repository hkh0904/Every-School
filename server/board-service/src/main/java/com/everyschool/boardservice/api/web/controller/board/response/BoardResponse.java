package com.everyschool.boardservice.api.web.controller.board.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {

    private Long boardId;
    private String title;
    private String writer;
    private LocalDateTime lastModifiedDate;

    @Builder
    public BoardResponse(Long boardId, String title, String writer, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.title = title;
        this.writer = writer;
        this.lastModifiedDate = lastModifiedDate;
    }
}
