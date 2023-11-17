package com.everyschool.boardservice.api.app.service.board.dto;

import com.everyschool.boardservice.domain.board.UploadFile;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateBoardDto {

    private String title;
    private String content;
    private Boolean isUsedComment;
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @Builder
    private CreateBoardDto(String title, String content, Boolean isUsedComment, List<UploadFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.isUsedComment = isUsedComment;
        this.uploadFiles = uploadFiles;
    }
}
