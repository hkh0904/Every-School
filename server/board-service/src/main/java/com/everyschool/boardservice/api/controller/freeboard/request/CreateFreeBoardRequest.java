package com.everyschool.boardservice.api.controller.freeboard.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateFreeBoardRequest {

    private String title;
    private String content;
    private Long categoryId;
    private Long schoolId;
    private Boolean useComment;
    private List<MultipartFile> uploadFiles;

    @Builder
    private CreateFreeBoardRequest(String title, String content, Long categoryId, Long schoolId, Boolean useComment, List<MultipartFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.schoolId = schoolId;
        this.useComment = useComment;
        this.uploadFiles = uploadFiles;
    }
}
