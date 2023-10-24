package com.everyschool.boardservice.api.controller.board.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class EditBoardRequest {

    private String title;
    private String content;
    private Long categoryId;
    private List<MultipartFile> uploadFiles;

    @Builder
    private EditBoardRequest(String title, String content, Long categoryId, List<MultipartFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.uploadFiles = uploadFiles;
    }
}
