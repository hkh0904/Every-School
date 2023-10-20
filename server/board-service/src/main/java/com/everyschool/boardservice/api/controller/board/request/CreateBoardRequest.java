package com.everyschool.boardservice.api.controller.board.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateBoardRequest {

    private String title;
    private String content;
    private Long categoryId;
    private Long schoolId;
    private Long classId;
    private Boolean useComment;
    private List<MultipartFile> uploadFiles;

    @Builder
    private CreateBoardRequest(String title, String content, Long categoryId, Long schoolId, Long classId, Boolean useComment, List<MultipartFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.schoolId = schoolId;
        this.classId = classId;
        this.useComment = useComment;
        this.uploadFiles = uploadFiles;
    }
}
