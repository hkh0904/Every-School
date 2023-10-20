package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateBoardResponse {

    private String userName;
    private String title;
    private String content;
    private int hit;
    private String categoryName;
    private LocalDateTime createdDate;
    private List<MultipartFile> uploadFiles;

    @Builder
    private CreateBoardResponse(String userName, String title, String content, int hit, String categoryName, LocalDateTime createdDate, List<MultipartFile> uploadFiles) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
        this.uploadFiles = uploadFiles;
    }
}
