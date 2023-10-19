package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardListResponse {
    private Long boardId;
    private String title;
    private String createDate;

    @Builder
    private BoardListResponse(Long boardId, String title, String createDate) {
        this.boardId = boardId;
        this.title = title;
        this.createDate = createDate;
    }
}
