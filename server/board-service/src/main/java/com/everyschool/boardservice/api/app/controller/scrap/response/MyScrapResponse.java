package com.everyschool.boardservice.api.app.controller.scrap.response;

import com.everyschool.boardservice.domain.board.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyScrapResponse {

    private Long boardId;
    private String type;
    private String title;
    private String content;
    private int commentCount;
    private LocalDateTime createdDate;
    private Boolean isTapped;

    @Builder
    public MyScrapResponse(Long boardId, int type, String title, String content, int commentCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.type = Category.getText(type);
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.isTapped = false;
    }
}
