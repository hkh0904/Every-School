package com.everyschool.boardservice.api.controller.freeboard.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FreeBoardListResponse {

    private Long boardId;
    private String title;
    private String createDate;
    private int commentNumber;

    @Builder
    public FreeBoardListResponse(Long boardId, String title, String createDate, int commentNumber) {
        this.boardId = boardId;
        this.title = title;
        this.createDate = createDate;
        this.commentNumber = commentNumber;
    }
}
