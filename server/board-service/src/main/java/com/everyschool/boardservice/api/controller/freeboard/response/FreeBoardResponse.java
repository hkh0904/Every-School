package com.everyschool.boardservice.api.controller.freeboard.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FreeBoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private String userName;
    private String createDate;
    private List<MultipartFile> uploadFiles;
    private List<CommentResponse> comments;

    @Builder
    public FreeBoardResponse(Long boardId, String title, String content, String userName, String createDate, List<MultipartFile> uploadFiles, List<CommentResponse> comments) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createDate = createDate;
        this.uploadFiles = uploadFiles;
        this.comments = comments;
    }
}
