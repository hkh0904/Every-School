package com.everyschool.boardservice.api.controller.board.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private String userName;
    private String createDate;
    private List<MultipartFile> uploadFiles;

    @Builder
    private BoardResponse(Long boardId, String title, String content, String userName, String createDate, List<MultipartFile> uploadFiles) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createDate = createDate;
        this.uploadFiles = uploadFiles;
    }
}
