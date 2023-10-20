package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private String userName;
    private String createDate;

    @Builder
    public BoardResponse(Long boardId, String title, String content, String userName, String createDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createDate = createDate;
    }
}
