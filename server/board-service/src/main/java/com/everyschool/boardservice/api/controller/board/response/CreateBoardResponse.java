package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBoardResponse {

    private String userName;
    private String title;
    private String content;
    private int hit;
    private String categoryName;
    private Long categoryId;
    private LocalDateTime createdDate;
    private int uploadFileNum;

    @Builder
    private CreateBoardResponse(String userName, String title, String content, int hit, String categoryName, Long categoryId, LocalDateTime createdDate, int uploadFileNum) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.createdDate = createdDate;
        this.uploadFileNum = uploadFileNum;
    }
}
