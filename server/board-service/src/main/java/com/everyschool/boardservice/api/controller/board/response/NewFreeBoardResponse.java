package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;

@Data
public class NewFreeBoardResponse {

    private Long boardId;
    private String title;

    @Builder
    public NewFreeBoardResponse(Long boardId, String title) {
        this.boardId = boardId;
        this.title = title;
    }
}
