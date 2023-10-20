package com.everyschool.boardservice.api.controller.freeboard.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateFreeBoardResponse {

    private String title;
    private String content;
    private int hit;
    private Long categoryId;
    private LocalDateTime createdDate;
    private int uploadFileNum;

    @Builder
    private CreateFreeBoardResponse(String title, String content, int hit, Long categoryId, LocalDateTime createdDate, int uploadFileNum) {
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.categoryId = categoryId;
        this.createdDate = createdDate;
        this.uploadFileNum = uploadFileNum;
    }
}
